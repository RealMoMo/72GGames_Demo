package momo.com.week12_project.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.se7en.utils.DeviceUtils;
import com.se7en.utils.SystemUtil;

import momo.com.week12_project.R;
import momo.com.week12_project.utils.BitmapUtil;

/**
 * Created by realmo on 2016/12/29 0029.
 */

public class WelcomeActivity extends BaseActivity {

    private ViewPager viewPager;
    private ImageView iv_start,iv_text,iv_logo;

    private final String VERSION_CODE = "versionCode";

    private ImageView[] ivArr;
    private int[] imgsId={
            R.mipmap.bg_guide_01,R.mipmap.bg_guide_02,R.mipmap.bg_guide_03,R.mipmap.bg_guide_04
    };
    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;

    }

    @Override
    protected void findViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
        iv_start = (ImageView) findViewById(R.id.iv_start_welcome);
        iv_text = (ImageView) findViewById(R.id.iv_text_welcome);
        iv_logo = (ImageView) findViewById(R.id.iv_logo_welcome);
    }



    @Override
    protected void initEvent() {
        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转到主页面
                //保存当前的版本号
                SystemUtil.setSharedInt(VERSION_CODE,SystemUtil.getSystemVersionCode());
                toMainActivity();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                    if(position == ivArr.length-1){
                        iv_start.setVisibility(View.VISIBLE);
                    }else{
                        iv_start.setVisibility(View.GONE);
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    protected void init() {
        //从SharedPreferences获取之前记录的app版本号
        int oldVersionCode = SystemUtil.getSharedInt(VERSION_CODE,-1);
        //获取当前app的版本号
        int currentVersionCode = SystemUtil.getSystemVersionCode();
        //第一次进入app或app更新
        if(oldVersionCode == -1 || oldVersionCode<currentVersionCode){
            //TODO 展示viewpager内容
            iv_text.setVisibility(View.GONE);
            iv_logo.setVisibility(View.GONE);
            iv_start.setVisibility(View.GONE);

            initViewPager();
        }else{
            //TODO 显示2个图片控件的动画
            viewPager.setVisibility(View.GONE);
            iv_start.setVisibility(View.GONE);
            iv_text.setVisibility(View.GONE);
            iv_logo.setVisibility(View.GONE);
            //只是为了进入WelcomeActivity后，延迟展示动画。
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO 执行动画
                    startWelcomeAnimation();
                }
            },200);

        }

    }


    @Override
    protected void loadData() {

    }

    private void initViewPager() {
        ivArr = new ImageView[imgsId.length];
        int ivW = DeviceUtils.getScreenWdith();
        int ivH = DeviceUtils.getScreenHeight();
        for (int i = 0; i < ivArr.length; i++) {
            ImageView iv = new ImageView(this);
            //处理oom
            Bitmap bitmap = BitmapUtil.getBitmap(getResources(),imgsId[i],ivW,ivH);
            iv.setImageBitmap(bitmap);
            ivArr[i] = iv;
        }
        viewPager.setAdapter(new WelcomeViewPagerAdapter());


    }


    private void startWelcomeAnimation() {

        //TODO 先执行文字的动画
        iv_text.setVisibility(View.VISIBLE);
        TranslateAnimation textAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,1f,Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
        textAnimation.setDuration(1000);
        textAnimation.setInterpolator(new OvershootInterpolator());
        textAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //TODO 文字动画结束后再执行logo的动画
                iv_logo.setVisibility(View.VISIBLE);
                TranslateAnimation logoAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0,
                        Animation.RELATIVE_TO_PARENT,-1f,Animation.RELATIVE_TO_SELF,0f);
                logoAnimation.setDuration(1000);
                logoAnimation.setInterpolator(new BounceInterpolator());
                iv_logo.startAnimation(logoAnimation);
                logoAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //TODO 跳转到主页面
                                toMainActivity();
                            }
                        },200);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_text.startAnimation(textAnimation);

    }

    /**
     * 跳转到主页面
     */
    private void toMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }



    class WelcomeViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return ivArr.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(ivArr[position]);
            return ivArr[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View)object);
        }
    }
}
