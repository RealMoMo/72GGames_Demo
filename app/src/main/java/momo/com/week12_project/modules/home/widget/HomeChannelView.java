package momo.com.week12_project.modules.home.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import momo.com.week12_project.R;

/**
 * Created by RealMo on 2016-12-31.
 */
public class HomeChannelView extends RelativeLayout{

    private String title,content;

    private int imgId;

    private TextView tv_title,tv_content;

    private ImageView img;
    //水波纹半径的最大值
    private float maxRadius;
    //水波纹半径的当前值
    private float currentRadius=-1;
    //水波纹半径半径每次的改变量
    private final float CHANGE_RADIUS=20;
    //点击控件的位置（水波纹的圆心位置）
    private float centerX,centerY;

    private Paint paint;

    //点击控件的放大倍数的阈值
    private final float THRESHOLD=0.1f;

    public HomeChannelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        //若是自定义的ViewGroup，加一句setWillNotDraw(false);//少了这一句Ondraw()方法可能不执行
        setWillNotDraw(false);

        View view = View.inflate(context, R.layout.layout_home_channel,this);
        tv_title = (TextView) view.findViewById(R.id.channel_home_title);
        tv_content  = (TextView) view.findViewById(R.id.channel_home_content);
        img = (ImageView) view.findViewById(R.id.channel_home_img);

        //获取自定义属性
        TypedArray typeArray = context.obtainStyledAttributes(attrs,R.styleable.HomeChannelView);
        title = typeArray.getString(R.styleable.HomeChannelView_channel_title);
        content = typeArray.getString(R.styleable.HomeChannelView_channel_content);
        imgId = typeArray.getResourceId(R.styleable.HomeChannelView_channel_thumbil,R.mipmap.ic_launcher);

        //给控件设置内容
        tv_title.setText(title);
        tv_content.setText(content);
        img.setBackgroundResource(imgId);

        //初始化paint
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#55ffffff"));


        //求出水波纹的最大半径值（对角线）
        post(new Runnable() {
            @Override
            public void run() {
                int width = getWidth();
                int height = getHeight();
                maxRadius = (float) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(currentRadius==-1|| currentRadius>maxRadius){
            return;
        }
        canvas.drawCircle(centerX,centerY,currentRadius,paint);
        currentRadius+=CHANGE_RADIUS;

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()== MotionEvent.ACTION_UP){
            //TODO 画水波纹
            currentRadius = 0; //重置初始状态
            centerX = event.getX();//event.getRawX代表相对于屏幕的原点X轴上的距离
            centerY = event.getY();
            invalidate();
            //TODO 缩放动画
            startScaleAnimation();
            //TODO 上述完成后，触发控件的点击事件。
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    //因为ontouchevent返回true。所以，此控件不能触发对应的点击事件。 用performClick可触发点击事件。
                    //点击事件的底层，也是调用此方法来触发点击事件。l
                 performClick();
                }
            },300);
        }

        return true;
    }

    private void startScaleAnimation() {
        ObjectAnimator animator=ObjectAnimator.ofFloat(this,"xxx",0,1).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress= (float) animation.getAnimatedValue();
                //当progress<0.5f就放大，否则缩小
                if(progress<0.5f){
                    setScaleX(1+progress*THRESHOLD*2);
                    setScaleY(1+progress*THRESHOLD*2);
                }else{
                    setScaleX(1+THRESHOLD-(progress-0.5f)*2*THRESHOLD);
                    setScaleY(1+THRESHOLD-(progress-0.5f)*2*THRESHOLD);
                }
            }
        });

        animator.start();


    }
}
