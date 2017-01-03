package momo.com.week12_project.modules.home.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.se7en.utils.DeviceUtils;

import momo.com.week12_project.R;
import momo.com.week12_project.modules.home.widget.HomeScrollView;
import momo.com.week12_project.ui.activity.MainActivity;
import momo.com.week12_project.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class HomeFragment extends BaseFragment implements HomeScrollView.GetScrollViewProgressListener, View.OnClickListener {

    private HomeScrollView homeScrollView;
    private ImageView iv_header;
    private LinearLayout ll_userinfo;
    private TextView tv_start;



    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void findViews(View view) {
        homeScrollView = (HomeScrollView) view.findViewById(R.id.homescrollview);
        iv_header = (ImageView) view.findViewById(R.id.iv_header_home);
        ll_userinfo = (LinearLayout) view.findViewById(R.id.ll_userinfo_home);
        tv_start = (TextView) view.findViewById(R.id.tv_start_home);

    }

    @Override
    protected void initEvent() {
        homeScrollView.setListener(this);
        //其点击跳转到第2个界面
        tv_start.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onProgress(float progress) {
        //userinfo 平移动画
        //userinfo控件的移动的最大值
        int llMaxOffset = (ll_userinfo.getWidth()+ DeviceUtils.getScreenWdith())/2;

        ll_userinfo.setTranslationX(llMaxOffset*progress);

        //iv_header 平移与缩放动画
        //iv_header控件的x与y轴的平移最大值
        int maxXOffset = (iv_header.getWidth()+DeviceUtils.getScreenWdith())/2;
        int maxYOffset = ll_userinfo.getHeight();
        //缩放动画
        iv_header.setPivotX(iv_header.getWidth()/2);
        iv_header.setPivotY(iv_header.getHeight());
        iv_header.setScaleX(1-progress*0.5f);
        iv_header.setScaleY(1-progress*0.5f);
        //平移动画
        iv_header.setTranslationX(-maxXOffset*progress);
        if(progress<=0.5f){
            iv_header.setTranslationY(maxYOffset*progress*2);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_start_home:{
                ((MainActivity)getActivity()).jumpToGameFragment();
            }break;
        }
    }
}
