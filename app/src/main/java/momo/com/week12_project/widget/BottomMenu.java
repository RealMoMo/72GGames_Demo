package momo.com.week12_project.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import momo.com.week12_project.R;

/**
 * 自定义View---菜单按钮
 */

public class BottomMenu extends RelativeLayout {
    //menu的文本
    private String text;
    //menu的图片id
    private int normalIconId, selectIconId;

    private TextView tvMenu;
    private ImageView ivIcon;

    //阈值
    private final float THRESHOLD = 0.5f;

    private Fragment fragment;


    public BottomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.layout_bottom_menu, this);
        tvMenu = (TextView) view.findViewById(R.id.tv_menu);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon_menu);

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomMenu);
        text = typedArray.getString(R.styleable.BottomMenu_menu_text);
        normalIconId = typedArray.getResourceId(R.styleable.BottomMenu_menu_normal_icon,R.mipmap.ic_default_normal);
        selectIconId = typedArray.getResourceId(R.styleable.BottomMenu_menu_select_icon,R.mipmap.ic_default_normal);

        //设置文本
        tvMenu.setText(text);
        //设置默认图片
        ivIcon.setImageResource(normalIconId);

    }

    //选中menu执行的动画
    public void selectMenu(){
        tvMenu.setVisibility(INVISIBLE);
        ivIcon.setImageResource(selectIconId);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(null,"xxx",0,1).setDuration(300);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                setPivotX(getWidth()/2);
                setPivotY(0);
                setScaleX(1+progress*THRESHOLD);
                setScaleY(1+progress*THRESHOLD);
            }
        });

        objectAnimator.start();

    }

    //没选中menu执行的动画
    public void unselectMenu(){
        tvMenu.setVisibility(VISIBLE);
        ivIcon.setImageResource(normalIconId);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(null,"xxx",1,0).setDuration(300);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                setPivotX(getWidth()/2);
                setPivotY(0);
                setScaleX(1+progress*THRESHOLD);
                setScaleY(1+progress*THRESHOLD);
            }
        });

        objectAnimator.start();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
