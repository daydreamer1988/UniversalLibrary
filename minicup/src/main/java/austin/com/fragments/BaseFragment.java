package austin.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import austin.com.R;


/**
 * Created by Austin on 2016/11/16.
 */

public abstract class BaseFragment extends Fragment {

    private LinearLayout placeHolder;
    private boolean isPrepared;
    private boolean isVisibleToUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), null, false);
        initView(view, savedInstanceState);
        placeHolder = (LinearLayout) view.findViewById(R.id.emptyPlaceHolder);
        if (placeHolder != null) {
            placeHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPlaceHolderClick();
                }
            });
        }

        isPrepared = true;
        if (isVisibleToUser) {
            onLazyLoad();
        }
        return view;
    }


    protected void showPlaceHolder(boolean show) {
        if (placeHolder != null) {
            placeHolder.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }


    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int setLayout();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.isVisibleToUser = true;
            if(isPrepared){
                onLazyLoad();
            }
        }else{
            this.isVisibleToUser = false;
            if (isPrepared) {
                onUnvisibleToUser();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
    }

    /**
     * 每次不可见时调用
     *
     */
    protected void onUnvisibleToUser() {}

    /**
     * 懒加载，每次可见时调用
     * 在子类中用ISFIRST来判断是否是第一次加载
     */
    protected void onLazyLoad(){}

    protected void onPlaceHolderClick(){}



}
