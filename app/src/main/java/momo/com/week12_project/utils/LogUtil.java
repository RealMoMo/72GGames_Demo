package momo.com.week12_project.utils;

import android.util.Log;

/**
 * Created by se7en on 16/7/8.
 */
public class LogUtil
{
    private static boolean isDebug = true;

    public static void i(String msg)
    {
        if(isDebug){
            Log.i("info", msg);
        }
    }

    public static void setDebug(boolean debug)
    {
        isDebug = debug;
    }
}
