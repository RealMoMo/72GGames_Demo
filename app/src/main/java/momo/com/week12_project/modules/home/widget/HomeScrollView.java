package momo.com.week12_project.modules.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.se7en.utils.DeviceUtils;

/**
 * 重写ScrollView的滚动监听的方法  ---实现头部的动画
 */

public class HomeScrollView extends ScrollView {

    //阈值
    private final int THRESHOLD = DeviceUtils.dip2px(100);

    private GetScrollViewProgressListener listener;

    public HomeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float progress = t*1f/THRESHOLD;
        if(progress>1){
            progress=1;
        }
        if(listener!=null){
            //TODO 让homefragment 实现头部2个控件的动画
            listener.onProgress(progress);
        }
    }



    public void setListener(GetScrollViewProgressListener listener){
        this.listener = listener;
    }

    public interface GetScrollViewProgressListener{

        public void onProgress(float progress);

    }
}
