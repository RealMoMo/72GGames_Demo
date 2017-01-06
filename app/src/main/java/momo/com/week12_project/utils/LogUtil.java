package momo.com.week12_project.utils;

import android.util.Log;

/**
 *
 */
public class LogUtil
{
    private static boolean isDebug = true;

    public static void i(String msg)
    {
        if(isDebug){
            Log.i("realmo", msg);
        }
    }

    public static void setDebug(boolean debug)
    {
        isDebug = debug;
    }
}
