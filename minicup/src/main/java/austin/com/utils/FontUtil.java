package austin.com.utils;

import android.content.res.Configuration;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by gy on 2017/7/26.
 */

public class FontUtil {
    /**
     * 获取系统字体大小
     * @return
     */
    public static float getFontSize() {
        // 获取系统字体大小
        try {
            Configuration mCurConfig = new Configuration();
            // 获取ActivityManagerNative类的对象activityManagerNative
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
                 /*
                  * 获得可调用getConfiguration方法的对象oam（调用 getDefault方法得到的东西）
                  * getMethod：获取AMN类中的getDefault方法
                  * invoke：通过activityManagerNative对象调用getDefault方法
                  */
            Object oam = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
            /* 获取getConfiguration方法并通过oam对象调用，得到config对象*/
            Object config = oam.getClass().getMethod("getConfiguration").invoke(oam);
            mCurConfig.updateFrom((Configuration) config);
            return mCurConfig.fontScale;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
