package momo.com.week12_project.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/6 0006.
 */

public class DownLoadUtils {
    /**
     * 进度更新
     */
    public final static int UPDATE_PROGRESS = 0x0001;
    /**
     * 下载开始
     */
    public final static int DOWNLOAD_START = 0x0002;
    /**
     * 下载完成
     */
    public final static int DOWNLOAD_FINISH = 0x0003;

    /**
     * 下载方法
     *
     * @param str     url
     * @param handler 当前状态更新时，发消息
     * @return
     * @throws Exception
     */
    public static Bitmap download(String str, Handler handler) throws Exception {
        //下载开始，通过handler发个下载开始通知
        if (handler != null) {
            Message msg = handler.obtainMessage();
            msg.what = DOWNLOAD_START;
            msg.obj = str;
            handler.sendMessage(msg);
        }

        HttpURLConnection conn = null;
        URL url = new URL(str);
        conn = (HttpURLConnection) url.openConnection();
        InputStream in = conn.getInputStream();
        int total = conn.getContentLength();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int tmp = 0;
        int sum = 0;
        while ((tmp = in.read(buffer)) != -1) {
            out.write(buffer, 0, tmp);
            sum += tmp;
            int per = (int) (sum * 100f / total);
            if (handler != null) {
                //进度更新，通过handler发消息
                Message msg = handler.obtainMessage();
                msg.what = UPDATE_PROGRESS;
                msg.arg1 = per;
                handler.sendMessage(msg);
            }
        }
        out.flush();
        Bitmap bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.toByteArray().length);
        if (handler != null) {
            //下载完成，通过handler发消息
            Message msg = handler.obtainMessage();
            msg.what = DOWNLOAD_FINISH;
            msg.obj = bitmap;
            handler.sendMessage(msg);
        }
        out.close();
        in.close();
        conn.disconnect();
        return bitmap;
    }

    /**
     * @param str      网址
     * @param fileName 保存的文件名
     * @param path     保存路径，不包含SD根目录
     * @param handler  用来发通知的Handler
     * @throws Exception
     */
    public static void saveFile(String str, String fileName, String path, Handler handler) throws Exception {
        //下载开始，通过handler发个下载开始通知
        if (handler != null) {
            Message msg = handler.obtainMessage();
            msg.what = DOWNLOAD_START;
            msg.obj = str;
            handler.sendMessage(msg);
        }

        HttpURLConnection conn = null;
        URL url = new URL(str);
        conn = (HttpURLConnection) url.openConnection();
        InputStream in = conn.getInputStream();
        int total = conn.getContentLength();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int tmp = 0;
        int sum = 0;
        while ((tmp = in.read(buffer)) != -1) {
            out.write(buffer, 0, tmp);
            sum += tmp;
            int per = (int) (sum * 100f / total);
            if (handler != null) {
                //进度更新，通过handler发消息
                Message msg = handler.obtainMessage();
                msg.what = UPDATE_PROGRESS;
                msg.arg1 = per;
                handler.sendMessage(msg);
            }
        }
        out.flush();
        //保存到SDcard
        String filePath = SDUtils.saveFile(out.toByteArray(), fileName, path);
        if (handler != null) {
            //下载完成，通过handler发消息
            Message msg = handler.obtainMessage();
            msg.what = DOWNLOAD_FINISH;
            msg.obj = filePath;
            handler.sendMessage(msg);
        }
        out.close();
        in.close();
        conn.disconnect();
    }
}
