package austin.com.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Austin on 2017/3/8.
 *
 *
 *WindowManger: context.getSystemService
 *
 *   windowManager.getDefaultDisplay().getSize(Point);
 *   windowManager.getDefaultDisplay().getRectSize(Rect);
 *   windowManager.getDefaultDisplay().getRealSize(Point);
 *   windowManager.getDefaultDisplay().getHeight();
 *   windowManager.getDefaultDisplay().getMatrics(DisplayMatrics);
 *
 *Context:
 *   Context.getResources().getDisplayMetrics().heightPixels;
 *   Context.getResources().getIdentifier("status_bar_height", "dimen", "android");
 *
 *
 *Window: Activity.getWindow
 *      activity.getWindow().getDecorView().getDrawingRect(Rect);
 *      activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
 *      activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
 *      activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
 */

public class DisplayUtil {


    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * onCreate中调用正常
     * 获取真正屏幕的物理尺寸
     * @param context
     * @return
     */
    public static int getRealScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point outSize = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(outSize);
        }else{
            wm.getDefaultDisplay().getSize(outSize);
        }
        return outSize.y;
    }

    /**
     * 不能在onCreate方法中调用
     * 获取真正屏幕的物理尺寸
     * 6.0(23) 7.0（24）正常
     * 4.4(19)不正常， 返回的是除底部Navigation的高度
     * @param activity
     * @return
     */
    public static int getRealScreenHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getDrawingRect(rect);
        return rect.height();
    }

    /**
     * 在onCreate调用正常
     * 获取除底部Navigation的高度（包括状态栏，标题栏，Drawing区）（标题栏与Drawing区又称为Application区）
     * @param context
     * @return
     */
    public static int getScreenHeight1(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * onCreate中调用正常
     * 获取除底部Navigation的高度（包括状态栏，标题栏，Drawing区）（标题栏与Drawing区又称为Application区）
     * @param context
     * @return
     */
    public static int getScreenHeight2(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * onCreate中调用正常
     * 获取除底部Navigation的高度（包括状态栏，标题栏，Drawing区）（标题栏与Drawing区又称为Application区）
     * @param context
     * @return
     */
    public static int getScreenHeight3(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Rect rect = new Rect();
        wm.getDefaultDisplay().getRectSize(rect);
        return rect.height();
    }

    /**
     * onCreate中调用正常
     * 获取除底部Navigation的高度（包括状态栏，标题栏，Drawing区）（标题栏与Drawing区又称为Application区）
     * @param context
     * @return
     */
    public static int getScreenHeight4(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * onCreate中调用正常
     * 获取除底部Navigation的高度（包括状态栏，标题栏，Drawing区）（标题栏与Drawing区又称为Application区）
     * @param context
     * @return
     */
    public static int getScreenHeight5(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 在onCreate方法获取成功
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }

    /**
     * 在onCreate方法获取成功
     * 利用反射方法
     * @param context
     * @return
     */
    public static int getStatusBarHeight2(Context context) {
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     *  onCreate获取0
     *  需要在onWindowFocusChanged()方法调用才正常
     *
     *  这种方法并不推荐大家使用，因为这种方法依赖于WMS（窗口管理服务的回调）。
     *  正是因为窗口回调机制，所以在Activity初始化时执行此方法得到的高度是0，这就是很多人获取到statusBar高度为0的原因。
     *  这个方法推荐在回调方法onWindowFocusChanged()中执行，才能得到预期结果。
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight3(Activity activity){
        Rect rect= new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 不能在onCreate中调用
     * @param activity
     * @return
     */
    public static int getStatusBarHeight4(Activity activity) {
        //屏幕
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //应用区域
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        //状态栏高度=屏幕高度-应用区域高度
        return dm.heightPixels - outRect.height();
    }

    /**
     * 需要在onWindowFocusChanged方法中调用
     * @param activity
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        return  getApplicationHeight(activity)-getDrawingHeight(activity);
    }

    /**
     * 不能在onCreate方法中调用
     * 可能返回0？
     * @param activity
     * @return
     */
    public static int getDrawingHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
        return rect.height();
    }
    /**
     * 不能在onCreate方法中调用
     * @param activity
     * @return
     */

    public static int getDrawingHeight2(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
    }

    /**
     * 不能在onCreate方法中调用
     * @param activity
     * @return
     */
    public static int getApplicationHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }








}