package austin.com.custom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy on 2017/9/15.
 */

public class FlowLayout extends ViewGroup {



    public FlowLayout(Context context) {
        this(context, null);
    }
    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        int targetWidth = 0;
        int targetHeight = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (childWidth + lineWidth > widthSize-getPaddingLeft()-getPaddingRight()) {//需要换行
                targetWidth = Math.max(lineWidth, targetWidth);
                targetHeight += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;

            }else{
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                targetWidth = Math.max(targetWidth, lineWidth);
                targetHeight += lineHeight;
            }
        }

        setMeasuredDimension(
                widthMode == MeasureSpec.AT_MOST ? targetWidth + getPaddingLeft() + getPaddingRight() : widthSize,
                heightMode == MeasureSpec.AT_MOST||heightMode==MeasureSpec.UNSPECIFIED ? targetHeight + getPaddingTop() + getPaddingBottom(): heightSize
        );

//        Log.e(TAG, "onMeasure: \ntargetWidth:" + targetWidth + "\ntargetHeight:" + targetHeight);
    }

    private static final String TAG = "FlowLayout";

    private List<List<View>> mAllViews = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        int layoutWidth = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineView = new ArrayList<>();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;


            if (lineWidth + childWidth > layoutWidth - getPaddingLeft() - getPaddingRight()) {//换行
                mLineHeight.add(lineHeight);
                mAllViews.add(lineView);
                lineView = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight;
                lineView = new ArrayList<>();
            }

            lineWidth += childWidth;
            lineHeight = Math.max(childHeight, lineHeight);
            lineView.add(childView);
        }

        mAllViews.add(lineView);
        mLineHeight.add(lineHeight);


        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lines = mAllViews.size();

        for (int i = 0; i < lines; i++) {
            lineView = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineView.size(); j++) {
                View view = lineView.get(j);
                if(view.getVisibility()==GONE){
                    continue;
                }
                MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
                if (view.getVisibility() == GONE) {
                    continue;
                }

                int lc = left + layoutParams.leftMargin;
                int tc = top + layoutParams.topMargin;
                int rc = lc + view.getMeasuredWidth();
                int bc = tc + view.getMeasuredHeight();

                view.layout(lc, tc, rc, bc);

                left += view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

            }

            left = getPaddingLeft();
            top += lineHeight;
        }
    }


    public void setData(List<String> mZanList) {
        removeAllViews();
        for (String s : mZanList) {
            TextView textView = new TextView(getContext());
            MarginLayoutParams layoutParams = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 0, 0, 0);
            textView.setTextColor(Color.parseColor("#295998"));
            textView.setLayoutParams(layoutParams);
            textView.setText(s);
            addView(textView, layoutParams);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
