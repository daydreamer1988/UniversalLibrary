package austin.com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.TypedValue;

import austin.com.activity.base.BaseActivity;


/**
 * Created by Austin on 2016/11/1.
 */
public class MiniCup {

    public static void startActivity(Context context, Class clz) {
        Intent intent = new Intent(context, clz);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Context context, Class clz, int requestCode) {
        Intent intent = new Intent(context, clz);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static int dp2Px(Context context, int dp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int dp2sp(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    public static boolean isActivityAlive(Context context) {
        if (context == null || ((BaseActivity)context).isFinishing()) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (((BaseActivity)context).isDestroyed()) {
                return false;
            }
        }
        return true;
    }
}
