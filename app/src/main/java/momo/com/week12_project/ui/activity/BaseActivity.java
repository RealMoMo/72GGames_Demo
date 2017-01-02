package momo.com.week12_project.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        findViews();
        initEvent();
        init();
        loadData();
    }

    /**
     * 设置布局id
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void findViews();

    /**
     * 处理控件的点击事件
     */
    protected abstract void initEvent();

    protected abstract void init();
    protected abstract void loadData();
}
