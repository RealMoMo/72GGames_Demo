package momo.com.week12_project.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflater用两个参数的方法不会加载布局中最外层控件的属性
        View view = inflater.inflate(setLayoutId(),container,false);

        findViews(view);
        initEvent();
        init();
        loadData();

        return view;
    }

    protected abstract int setLayoutId();

    protected abstract void findViews(View view);
    protected abstract void initEvent();
    protected abstract void init();
    protected abstract void loadData();

}
