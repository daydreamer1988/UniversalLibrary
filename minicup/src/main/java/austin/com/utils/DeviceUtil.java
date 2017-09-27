package austin.com.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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


    /**
     * 获取设备的IMEI码
     *
     * @return
     */
    public static String getIMEI() {

        StringBuilder sb = new StringBuilder();

        Field[] fields = Build.class.getFields();

        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    sb.append("\t" + field.getName() + ": ").append(field.get(null).toString()).append('\n');
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        String s = MD5Util.encrypt(sb.toString()).toUpperCase();

        return s;
//        return ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
    }

    public static String getLocalIpAddress() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }
}
