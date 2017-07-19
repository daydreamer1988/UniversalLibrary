package austin.com.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import austin.com.BuildConfig;
import austin.com.MyApplication;


/**
 * Created by Austin on 2016/10/28.
 */

public class RequestUtils {
    public static String TAG = "RequestUtils";

    public static void GetStringRequest(Context ct, String url, Map<String, String> params, VolleyInterface volleyInterface) {

        StringBuffer urlsb = new StringBuffer();
        urlsb.append(url + "?");

        Set<Map.Entry<String, String>> entries = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            urlsb.append(key + "=" + value + "&");
        }

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Get StringRequest Url: " + urlsb.toString());
        }

        volleyInterface.syncContext(ct);
        MyApplication.getInstance().getRequestQueue().cancelAll(ct.getClass().getName());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlsb.toString(), volleyInterface.initStringLisener(), volleyInterface.initErrorLisener());
        stringRequest.setTag(ct.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    public static void PostStringRequest(Context context, String url, final Map<String, String> params, VolleyInterface volleyInterface) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Post StringRequest Url: " + url);
            Log.e(TAG, "Post StringRequest Params: " + params.toString());
        }
        volleyInterface.syncContext(context);
        MyApplication.getInstance().getRequestQueue().cancelAll(context.getClass().getName());
        StringRequest request = new StringRequest(Request.Method.POST, url, volleyInterface.initStringLisener(), volleyInterface.initErrorLisener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        request.setTag(context.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(request);
    }


    public static void GetJsonObjectRequest(Context context, String url, Map<String, String> params, VolleyInterface volleyInterface) {

        StringBuffer urlsb = new StringBuffer();
        urlsb.append(url + "?");

        Set<Map.Entry<String, String>> entries = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            urlsb.append(key + "=" + value + "&");
        }

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Get JsonObjectRequest Url: " + urlsb.toString());
        }

        JSONObject jsonObject = new JSONObject(params);
        volleyInterface.syncContext(context);
        MyApplication.getInstance().getRequestQueue().cancelAll(context.getClass().getName());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, volleyInterface.initJsonObjectListener(), volleyInterface.initErrorLisener());
        jsonObjectRequest.setTag(context.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);


    }


    public static void PostJsonObjectRequest(Context context, String url, Map<String, String> params, VolleyInterface volleyInterface) {
        JSONObject jsonObject = new JSONObject(params);
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Post JsonObjectRequest Url: " + url);
            Log.e(TAG, "Post JsonObjectRequest Params: " + jsonObject.toString());
        }
        volleyInterface.syncContext(context);
        MyApplication.getInstance().getRequestQueue().cancelAll(context.getClass().getName());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, volleyInterface.initJsonObjectListener(), volleyInterface.initErrorLisener());
        jsonObjectRequest.setTag(context.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);
    }



    public static void PostJsonArrayRequest(Context context, String url, final Map<String, List<String>> params, VolleyInterface volleyInterface) {
        JSONObject jsonObject = new JSONObject(params);
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Post JsonArrayRequest Url: " + url);
            Log.e(TAG, "Post JsonArrayRequest Params: " + jsonObject.toString());
        }
        volleyInterface.syncContext(context);
        MyApplication.getInstance().getRequestQueue().cancelAll(context.getClass().getName());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, jsonObject, volleyInterface.initArrayLisener(), volleyInterface.initErrorLisener());
        request.setTag(context.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(request);

    }
}


