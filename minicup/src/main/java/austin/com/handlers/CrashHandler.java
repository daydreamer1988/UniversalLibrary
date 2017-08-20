package austin.com.handlers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 *问题1：在MainActivity onCreate中抛异常，onCreate会被执行三次，异常会被捕获三次，日志会被写三次
 *
 *问题2：Throwable中的cause是无限循环的
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static CrashHandler mInstance;
    private UncaughtExceptionHandler sDefaultHandler;
    private Context mContext;
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss", Locale.getDefault());
    private StringBuilder mCrashInfo;


    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        mCrashInfo = new StringBuilder();
        sDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex)) {
            if (sDefaultHandler != null)
                sDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 是否自定义捕获异常
     *
     * @param ex
     * @return
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) return false;

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "Custom Handle Crash", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        collectErrorInfo();
        saveErrorInfo(ex);
        return true;
    }

    private void saveErrorInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);

        /*Throwable cause = ex.getCause();
        while(cause!=null){
            cause.printStackTrace(printWriter);
            cause = ex.getCause();
        }*/

        printWriter.close();

        String result = writer.toString();
        mCrashInfo.append('\n');
        mCrashInfo.append(result);

        String fileName = "Crash_" + timeFormat.format(new Date()) + ".txt";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path + File.separator + fileName);
                fos.write(mCrashInfo.toString().getBytes());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void collectErrorInfo() {
        PackageManager pm = mContext.getPackageManager();
        ApplicationInfo ai = mContext.getApplicationInfo();

        mCrashInfo.append("APPLICATION INFOMATION:").append('\n');
        mCrashInfo.append("\tApplication Name : ").append(pm.getApplicationLabel(ai)).append('\n');
        try {
            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
            mCrashInfo.append("\tVersion Code: ").append(pi.versionCode).append('\n');
            mCrashInfo.append("\tVersion Name: ").append(pi.versionName).append('\n');
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mCrashInfo.append('\n');
        mCrashInfo.append("DEVICE INFORMATION").append('\n');

        Field[] fields = Build.class.getFields();

        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    mCrashInfo.append("\t" + field.getName() + ": ").append(field.get(null).toString()).append('\n');
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

       /* mCrashInfo.append("\tBoard: ").append(Build.BOARD).append('\n');
        mCrashInfo.append("\tBOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
        mCrashInfo.append("\tBRAND: ").append(Build.BRAND).append('\n');
        mCrashInfo.append("\tCPU_ABI: ").append(Build.CPU_ABI).append('\n');
        mCrashInfo.append("\tCPU_ABI2: ").append(Build.CPU_ABI2).append('\n');
        mCrashInfo.append("\tDEVICE: ").append(Build.DEVICE).append('\n');
        mCrashInfo.append("\tDISPLAY: ").append(Build.DISPLAY).append('\n');
        mCrashInfo.append("\tFINGERPRINT: ").append(Build.FINGERPRINT).append('\n');
        mCrashInfo.append("\tHARDWARE: ").append(Build.HARDWARE).append('\n');
        mCrashInfo.append("\tHOST: ").append(Build.HOST).append('\n');
        mCrashInfo.append("\tID: ").append(Build.ID).append('\n');
        mCrashInfo.append("\tMANUFACTURER: ").append(Build.MANUFACTURER).append('\n');
        mCrashInfo.append("\tPRODUCT: ").append(Build.PRODUCT).append('\n');
        mCrashInfo.append("\tTAGS: ").append(Build.TAGS).append('\n');
        mCrashInfo.append("\tTYPE: ").append(Build.TYPE).append('\n');
        mCrashInfo.append("\tUSER: ").append(Build.USER).append('\n');
        mCrashInfo.append("\tMODEL: ").append(Build.MODEL).append('\n');
        mCrashInfo.append("\tRELEASE: ").append(Build.VERSION.RELEASE).append('\n');*/


    }


}
