package momo.com.week12_project.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import java.io.File;

import momo.com.week12_project.R;

/**
 * 开启服务下载下载app
 */
public class DownLoadService extends Service {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DownLoadUtils.DOWNLOAD_START:{
                    //发一个通知
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(DownLoadService.this);
                    builder.setTicker("开始下载")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("开始下载APK")
                            .setContentText("下载Apk");
                    builder.setProgress(100,0,true);

                    NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(100,builder.build());

                }break;
                case DownLoadUtils.UPDATE_PROGRESS:{
                    //发一个通知
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(DownLoadService.this);
                    builder.setTicker("正在下载")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("正在下载APK")
                            .setContentText("下载Apk中...");
                    //获取当前进度
                    int per =msg.arg1;
                    builder.setProgress(100,per,false);

                    NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(100,builder.build());

                }break;
                case DownLoadUtils.DOWNLOAD_FINISH:{

                    //发一个通知
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(DownLoadService.this);
                    builder.setTicker("下载完成")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("下载完成")
                            .setContentText("Apk已下载完成");

                    //下载完成后，点击通知，进入安装界面
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //取出文件路径　
                    String filePath = (String) msg.obj;
                    //构建文件名的Uri
                    Uri uri = Uri.fromFile(new File(filePath));
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    //intent-->PendingIntent
                    PendingIntent pendingIntent = PendingIntent.getActivity(DownLoadService.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    //关联PendingIntent
                    builder.setContentIntent(pendingIntent);

                    builder.setAutoCancel(true);


                    NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(100,builder.build());


                }break;
            }
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String url = intent.getStringExtra(Constant.DOWNLOAD);

        ThreadTask.getInstance().executorNetThread(new Runnable() {
            @Override
            public void run() {
                //http://dd-10038606.cos.myqcloud.com/dgsd1125/dgsd_72g_yuu1.apk  --->dgsd_72g_yuu1.apk
                String apkName = url.substring((url.lastIndexOf("/")+1),url.length());

                try {
                    DownLoadUtils.saveFile(url,apkName,Constant.APK_FILE,handler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },100);

        return super.onStartCommand(intent, flags, startId);
    }


}
