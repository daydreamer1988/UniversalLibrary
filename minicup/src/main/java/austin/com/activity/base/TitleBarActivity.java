package austin.com.activity.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import austin.com.R;
import austin.com.interfaces.TitleBarInterface;


/**
 * Created by Austin on 2016/10/28.
 */

public class TitleBarActivity extends BaseActivity implements TitleBarInterface {


    private TextView titleTV;
    private TextView rightText;
    private boolean showRightText = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarTintManager manager = new SystemBarTintManager(this);
        manager.setStatusBarTintEnabled(true);
        manager.setStatusBarTintColor(getResources().getColor(TITLE_BAR_BG));
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        titleTV = (TextView) findViewById(R.id.title);
        if (titleTV != null) {
            titleTV.setText(setTitle());
        }

        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        if (back != null) {
            ImageView imageLeft = (ImageView) back.getChildAt(0);
            int leftImageResource = setLeftImageResource();
            if (leftImageResource != -1) {
                imageLeft.setImageResource(leftImageResource);
            }
            View.OnClickListener onClickListener = onMyBackPressed();
            if (onClickListener == null) {
                back.setVisibility(View.INVISIBLE);
            }else{
                back.setVisibility(View.VISIBLE);
                back.setOnClickListener(onClickListener);
            }
        }

        showRightText = !setRightTitle().equals("") || setRightImage()!=-1;
        rightText = (TextView) findViewById(R.id.rightText);
        if (showRightText) {
            rightText.setVisibility(View.VISIBLE);
            if(!setRightTitle().equals("")) {
                rightText.setText(setRightTitle());

            }
            if (onRightTextClicked() != null) {
                rightText.setOnClickListener(onRightTextClicked());
            }

            int i = setRightImage();
            if (i != -1) {
                Drawable drawable = getResources().getDrawable(i);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                rightText.setCompoundDrawables(null, null, drawable, null);
            }else{
                rightText.setCompoundDrawables(null, null, null, null);

            }
        }

        rightText = (TextView) findViewById(R.id.rightText);
        if (showRightText) {
            rightText.setVisibility(View.VISIBLE);
            if(!setRightTitle().equals("")) {
                rightText.setText(setRightTitle());
            }
            if (onRightTextClicked() != null) {
                rightText.setOnClickListener(onRightTextClicked());
            }
        }
    }

    /**
     *
     * @return
     */
    public int setLeftImageResource() {
        return -1;
    }
    @Override
    public View.OnClickListener onMyBackPressed() {
        return null;
    }
    /**
     *
     * @return
     */
    @Override
    public String setTitle() {
        return "";
    }

    public void animateTitle(final String title) {
        if (titleTV != null) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(titleTV, "alpha", 1, 0).setDuration(100);
            alpha.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    titleTV.setText(title);
                    ObjectAnimator.ofFloat(titleTV, "alpha", 0, 1).setDuration(100).start();
                }
            });
            alpha.start();
        }
    }
    /**
     *
     * @return
     */
    @Override
    public int setRightImage() {
        return -1;
    }
    @Override
    public String setRightTitle() {
        return "";
    }
    public String getRightTitle(){
        return rightText.getText().toString();
    }
    @Override
    public View.OnClickListener onRightTextClicked() {
        return null;
    }





}
