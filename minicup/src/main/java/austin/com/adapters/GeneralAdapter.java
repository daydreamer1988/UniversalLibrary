package austin.com.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public abstract class GeneralAdapter<T> extends BaseAdapter {
    public int layoutId;
    private List<T> tList = new ArrayList<>();//数据的存放流
    public Context context;


    /**
     * @param context    上下文对象
     * @param layoutId   布局id
     * @param collection 集合数据
     */
    public GeneralAdapter(Context context, int layoutId, Collection<? extends T> collection) {
        this.context = context;
        this.layoutId = layoutId;
        tList.addAll(collection);

    }

    /**
     * @param context  上下文对象
     * @param layoutId 布局id
     */
    public GeneralAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;


    }

    /**
     * 刷新单个item
     *
     * @param index
     * @param listView
     */
    public View getView(int index, AbsListView listView) {
        int position = listView.getFirstVisiblePosition();
        return listView.getChildAt(index - position);

    }

    /**
     * 添加一个对象
     *
     * @param entity
     */
    public void add(T entity) {
        if (entity == null) {
            return;
        }
        tList.add(entity);
        notifyDataSetChanged();
    }

    public void add(T e, int positon) {
        if (e == null) {
            return;
        }
        tList.add(positon, e);
        notifyDataSetChanged();
    }

    /**
     * 移除对象
     *
     * @param entity
     */
    public void remove(T entity) {
        if (entity == null) {
            return;
        }
        tList.remove(entity);
        notifyDataSetChanged();
    }

    /**
     * 移除对象
     *
     * @param i
     */
    public void remove(int i) {
        tList.remove(i);
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return tList;
    }

    /**
     * 添加一个集合数组
     *
     * @param collection
     */
    public void addAll(Collection<? extends T> collection) {
        if (collection == null) {
            return;
        }
        tList.addAll(collection);
        notifyDataSetChanged();
    }

    /**
     * 添加一个集合数组
     *
     * @param collection
     */
    public void addAll(Collection<? extends T> collection, boolean flag) {
        if (collection == null) {
            return;
        }
        if (flag)
            tList.clear();
        tList.addAll(collection);
        notifyDataSetChanged();
    }

    /**
     * 替换适配器的数据
     *
     * @param collection
     */
    public void replaceAll(Collection<? extends T> collection) {
        if (collection == null) {
            return;
        }
        tList.clear();
        tList.addAll(collection);
        notifyDataSetChanged();
    }

    /**
     * 清空适配器
     */
    public void clean() {
        tList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tList.size();
    }

    @Override
    public T getItem(int i) {
        return tList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdapterHelper helper = getViewHolder(i, view, viewGroup);
        convert(helper, getItem(i));
        return helper.getConvertView();
    }

    public abstract void convert(AdapterHelper helper, T item);


    private AdapterHelper getViewHolder(int position, View convertView,
                                        ViewGroup parent) {
        return AdapterHelper.get(context, convertView, parent, layoutId,
                position);
    }
}
