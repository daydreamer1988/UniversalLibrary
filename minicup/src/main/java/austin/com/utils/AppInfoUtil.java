package austin.com.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 获取程序内部数据
 */

public class AppInfoUtil {

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return packageManager.getApplicationLabel(applicationInfo).toString();
    }


    public static String getPackageName2(Context context) {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }

    /**
     * 获取应用包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context)//
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo applicationInfo = pi.applicationInfo;
            return applicationInfo.packageName;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

}