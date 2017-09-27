package austin.com.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 1.标题固定：在XML中设置与一般ScrollView无异
 * 2.标题不固定，网络请求后将每一个Item通过addView放到ScrollView中的LinearLayout中。
 */
public class CenterScrollView extends HorizontalScrollView {

    private int screenWidth;
    private OnScrollChangListener scrollListener;

    public CenterScrollView(Context context) {
        this(context,null);
    }

    public CenterScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        localInit(context);
    }

    private void localInit(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();

    }

    /**
     * 点击其中的ITEM时，自动滚动到中间
     * @param view
     */
    public void setCenterView(View view){
        LinearLayout parent = (LinearLayout) getChildAt(0);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (view == childAt) {
                float left = childAt.getLeft();
                int width = getWidth();
                smoothScrollTo((int) (left - width / 2+view.getWidth()/2), 0);
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollListener!=null)
            scrollListener.onScrollChange(this, l, t, oldl, oldt);
    }

    public void setOnScrollChangeListener(OnScrollChangListener l) {
        scrollListener = l;
    }

    public interface OnScrollChangListener {
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
