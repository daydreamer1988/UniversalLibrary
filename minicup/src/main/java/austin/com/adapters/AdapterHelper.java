package austin.com.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class AdapterHelper {
    private final SparseArray<View> mViews;
    private static int mPosition;
    private View mConvertView;
    public Context context;

    private AdapterHelper(Context context, ViewGroup parent, int layoutId,
                          int position) {
        mPosition = position;
        this.context = context;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, parent,
                false);
        ((ViewGroup) mConvertView).setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static AdapterHelper get(Context context, View convertView,
                                    ViewGroup parent, int layoutId, int position) {
        mPosition = position;
        if (convertView == null) {
            return new AdapterHelper(context, parent, layoutId, position);
        }
        return (AdapterHelper) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public AdapterHelper setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public AdapterHelper setBackgroundColor(int viewId, int color) {
        TextView view = getView(viewId);
        view.setBackgroundColor(color);

        return this;
    }

    public AdapterHelper setBackgroundResource(int viewId, int resouid) {
        View view = getView(viewId);

        view.setBackgroundResource(resouid);
        return this;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public AdapterHelper setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public AdapterHelper setChecked(int viewId, boolean flag) {
        CheckBox view = getView(viewId);
        view.setChecked(flag);
        return this;
    }

    public AdapterHelper setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public AdapterHelper setMore(int viewid, Object s) {
        String str = String.valueOf(s);
        int num = 0;
        int indexof = 0;
        while ((indexof = str.indexOf("\n", indexof + 1)) != -1) {
            num++;
        }
        if (str.length() > 100 || num > 4) {
            getView(viewid).setVisibility(View.VISIBLE);
        } else {
            getView(viewid).setVisibility(View.GONE);
        }

        return this;
    }

    public AdapterHelper setTextColor(int viewId, int text) {
        TextView view = getView(viewId);
        view.setTextColor(text);
        return this;
    }

    public AdapterHelper setNumColumns(int viewId, int num) {
        GridView view = getView(viewId);
        view.setNumColumns(num);
        return this;
    }

    /**
     * 为TextView字符
     *
     * @param viewId
     * @param text
     * @return
     */
    public AdapterHelper setText(int viewId, SpannableStringBuilder text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public AdapterHelper setText(int viewId, Spanned text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 点击事件
     *
     * @param viewId
     * @param onClickListener
     * @return
     */
    public AdapterHelper setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 是否可点
     *
     * @param viewId
     * @param flag
     * @return
     */
    public AdapterHelper setClickable(int viewId, boolean flag) {
        getView(viewId).setClickable(flag);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public AdapterHelper setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }


    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public AdapterHelper setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }


    /**
     * 设置隐藏 显示
     *
     * @param viewId
     * @param flag
     * @return
     */
    public AdapterHelper setGone(int viewId, boolean flag) {
        View view = getView(viewId);
        if (flag) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * 隐藏
     *
     * @param viewId
     * @return
     */
    public AdapterHelper setGone(int viewId) {
        getView(viewId).setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置不显示 显示
     *
     * @param viewId
     * @param flag
     * @return
     */
    public AdapterHelper setInvisible(int viewId, boolean flag) {
        View view = getView(viewId);
        if (flag) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public AdapterHelper setTag(int viewId, Object o) {
        getView(viewId).setTag(o);
        return this;
    }

    public AdapterHelper setTagView(int viewId,@IdRes int ids) {
        getView(viewId).setTag(getView(ids));
        return this;
    }

    public AdapterHelper setInvisible(int viewId) {
        getView(viewId).setVisibility(View.INVISIBLE);
        return this;
    }

    public AdapterHelper setVisible(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
        return this;
    }

    public AdapterHelper setAdapter(int viewId, BaseAdapter adapter) {
        AbsListView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public int getVisible(int viewId) {
        return getView(viewId).getVisibility();
    }

    public int getPosition() {
        return mPosition;
    }

}
