package austin.com.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 有效解决了ListView嵌套ListView中，ListView的高度问题，及ListView Item 中包含TextView不换行问题
 *
 */
public class NestedListView extends ListView {
    public NestedListView(Context context) {
        this(context,null);
    }

    public NestedListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
