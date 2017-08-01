package austin.com.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by gy on 2017/7/31.
 */

public class DeviceUtil {

    /**
     * 获取设备的IMEI码
     *
     * @return
     */
    public static String getIMEI(Context context) {
        return ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
    }
}
