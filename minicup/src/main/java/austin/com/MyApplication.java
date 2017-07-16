package austin.com;

import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import austin.com.utils.SharedPreferencesUtils;

/**
 * Created by Austin on 2016/10/28.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    private RequestQueue requestQueue;

    private static List<FragmentActivity> activities;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        SharedPreferencesUtils.init(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        activities = new ArrayList<>();

    }

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public static void addActity(FragmentActivity activity) {
        activities.add(activity);
    }

    public static void exitApp(){
        for (FragmentActivity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
