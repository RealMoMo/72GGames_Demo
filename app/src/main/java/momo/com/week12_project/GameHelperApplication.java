package momo.com.week12_project;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.se7en.utils.DeviceUtils;
import com.se7en.utils.SystemUtil;

import java.io.File;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class GameHelperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化自己导的包
        initLibs();
        //初始化ImageLoader第三方框架
        initImageLoader();
    }


    private void initLibs() {
        DeviceUtils.setContext(this);
        SystemUtil.setContext(this);
    }

    private void initImageLoader() {
        String path ;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"gamehelper";
        }else{
            path = Environment.getDataDirectory().getAbsolutePath() + File.separator + "gamehelper";
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).diskCache(new UnlimitedDiskCache(new File(path))).memoryCache(new LruMemoryCache((int) (Runtime.getRuntime().maxMemory()/8))).threadPoolSize(8).build();
        ImageLoader.getInstance().init(config);
    }
}
