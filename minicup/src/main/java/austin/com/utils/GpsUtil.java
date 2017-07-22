package austin.com.utils;

import android.content.Context;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Austin on 2017/4/10.
 */

public class GpsUtil {

    public static boolean isGpsOpened(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
