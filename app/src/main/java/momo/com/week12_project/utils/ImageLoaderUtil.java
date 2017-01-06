package momo.com.week12_project.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import momo.com.week12_project.R;


/**
 *
 */
public class ImageLoaderUtil
{

    /**
     * 获取一个默认的配置
     * 
     * @return
     */
    public static DisplayImageOptions getDefaultOption()
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .
                // 网络图片下载完成之前的预加载的默认图片
                showImageOnLoading(R.mipmap.ic_launcher)
                .
                // 网络图片下载失败后显示该默认图片
                showImageOnFail(R.mipmap.ic_launcher)
                .
                // 图片的质量
                bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true)
                .cacheOnDisk(true).build();
        return options;
    }

    /**
     * 获取一个圆形的配置
     * 
     * @return
     */
    public static DisplayImageOptions getCircleOption(Integer strokeColor,
            float strokeWidth)
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .
                // 网络图片下载完成之前的预加载的默认图片
                showImageOnLoading(R.mipmap.ic_launcher)
                .
                // 网络图片下载失败后显示该默认图片
                showImageOnFail(R.mipmap.ic_launcher)
                .
                // 图片的质量
                bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer(strokeColor, strokeWidth))
                .build();
        return options;
    }

    /**
     * 获取一个圆角的配置
     * 
     * @return
     */
    public static DisplayImageOptions getRoundBitmapOption(
            int cornerRadiusPixels, int marginPixels)
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .
                // 网络图片下载完成之前的预加载的默认图片
                showImageOnLoading(R.mipmap.ic_launcher)
                .
                // 网络图片下载失败后显示该默认图片
                showImageOnFail(R.mipmap.ic_launcher)
                .
                // 图片的质量
                bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(
                        new RoundedBitmapDisplayer(cornerRadiusPixels,
                                marginPixels)).build();
        return options;
    }

    /**
     * 获取一个渐显图片的配置
     * 
     * @return
     */
    public static DisplayImageOptions getFadeInOption(int durationMillis,
            boolean animateFromNetwork, boolean animateFromDisk,
            boolean animateFromMemory)
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .
                // 网络图片下载完成之前的预加载的默认图片
                showImageOnLoading(R.mipmap.ic_launcher)
                .
                // 网络图片下载失败后显示该默认图片
                showImageOnFail(R.mipmap.ic_launcher)
                .
                // 图片的质量
                bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(
                        new FadeInBitmapDisplayer(durationMillis,
                                animateFromNetwork, animateFromDisk,
                                animateFromMemory)).build();
        return options;
    }
}
