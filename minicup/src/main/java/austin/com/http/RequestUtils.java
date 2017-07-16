package austin.com.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import austin.com.BuildConfig;
import austin.com.MyApplication;
import austin.com.utils.MD5Util;

import static austin.com.globle.Appconstants.URL_KEY;
import static austin.com.globle.Appconstants.URL_NAME;
import static austin.com.globle.Appconstants.URL_SIGN_KEY;
import static austin.com.globle.Appconstants.URL_TIMESTAMP_KEY;

/**
 * Created by Austin on 2016/10/28.
 */

public class RequestUtils {
    public static String TAG = "URL_REQUEST";
    public static StringRequest stringRequest;
    public static JsonObjectRequest jsonObjectRequest;

    public static void getStringRequest2(Context ct, String url, Map<String, String> params, VolleyInterface vi) {
        vi.syncContext(ct);
        MyApplication.getInstance().getRequestQueue().cancelAll(ct.getClass().getName());
        stringRequest = new StringRequest(Request.Method.GET, url, vi.initStringLisener(), vi.initErrorLisener());
        stringRequest.setTag(ct.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /**
     *
     * Get请求
     * StringRequest
     *
     * @param ct
     * @param url
     * @param params
     * @param vi
     */
    public static void getStringRequest(Context ct, String url, Map<String, String> params, VolleyInterface vi) {
        StringBuffer sb = new StringBuffer();

        /**
         * 排序参数
         */
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(params);

        Set<Map.Entry<String, String>> sortedData = sortMap.entrySet();

        long timestamp = System.currentTimeMillis()/1000;

        /**
         * 按照相应规则字符串拼接
         */
        sb.append(URL_NAME).append(URL_KEY).append(timestamp);

        for(Map.Entry<String, String> entry : sortedData) {
            sb.append(entry.getValue());
        }

        /**
         * 加密
         */
        String MD5 = MD5Util.encrypt(sb.toString()).toUpperCase();


        params.put(URL_SIGN_KEY, MD5);
        params.put(URL_TIMESTAMP_KEY, timestamp+"");

        /**
         * 拼接url
         */
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

        vi.syncContext(ct);
        MyApplication.getInstance().getRequestQueue().cancelAll(ct.getClass().getName());
        stringRequest = new StringRequest(Request.Method.GET, urlsb.toString(), vi.initStringLisener(), vi.initErrorLisener());
        stringRequest.setTag(ct.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /**
     *
     * Post请求
     * StringRequest
     *
     */

    public static void postStringRequest(Context context, String url, final Map<String, String> params, VolleyInterface volleyInterface) {
        StringBuffer sb = new StringBuffer();

        //排序
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(params);

        Set<Map.Entry<String, String>> entries = sortMap.entrySet();

        long timestamp = System.currentTimeMillis();

        sb.append(URL_NAME).append(URL_KEY).append(timestamp);

        for(Map.Entry<String, String> entry : entries) {
            sb.append(entry.getValue());
        }

        String MD5 = MD5Util.encrypt(sb.toString()).toUpperCase();
        params.put(URL_SIGN_KEY, MD5);
        params.put(URL_TIMESTAMP_KEY, timestamp+"");


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

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Post StringRequest Url: " + url);
            Log.e(TAG, "Post StringRequest Params: " + params.toString());
        }
    }


    /**
     * Post请求
     * JsonObjectRequest
     *
     * @param context
     * @param url
     * @param params
     * @param volleyInterface
     */
    public static void postJsonObjectRequest(Context context, String url, Map<String, String> params, VolleyInterface volleyInterface) {
        StringBuffer sb = new StringBuffer();

        //参数排序
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(params);

        Set<Map.Entry<String, String>> entries = sortMap.entrySet();

        long timestamp = System.currentTimeMillis();

        sb.append(URL_NAME).append(URL_KEY).append(timestamp);

        for (Map.Entry<String, String> entry : entries) {
            sb.append(entry.getValue());
        }

        String MD5 = MD5Util.encrypt(sb.toString()).toUpperCase();
        params.put(URL_SIGN_KEY, MD5);
        params.put(URL_TIMESTAMP_KEY, timestamp + "");


        volleyInterface.syncContext(context);
        MyApplication.getInstance().getRequestQueue().cancelAll(context.getClass().getName());
        JSONObject jsonObject = new JSONObject(params);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, volleyInterface.initJsonObjectListener(), volleyInterface.initErrorLisener());
        jsonObjectRequest.setTag(context.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "PostUrl: " + url);
            Log.e(TAG, "PostParams: " + jsonObject.toString());
        }
    }


    /**
     * Post请求
     * JsonArrayRequest
     * 服务器返回的是JsonArray
     *
     * @param context
     * @param url
     * @param params
     * @param volleyInterface  VolleyInterface<JSONArray>
     */
    public static void postArrayRequestForJianHu(Context context, String url, final Map<String, List<String>> params, VolleyInterface volleyInterface) {

        volleyInterface.syncContext(context);
        MyApplication.getInstance().getRequestQueue().cancelAll(context.getClass().getName());

        JSONObject jsonObject = new JSONObject(params);
        JsonArrayRequest request1= new JsonArrayRequest(Request.Method.POST,url, jsonObject, volleyInterface.initArrayLisener(), volleyInterface.initErrorLisener());
        request1.setTag(context.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(request1);

        if(BuildConfig.DEBUG) {
            Log.e(TAG, "PostUrl: " + url);
            Log.e(TAG, "PostParams: " + jsonObject.toString());
        }
    }


    public static void postJsonRequest(Context context, String url, Map<String, String> params, VolleyInterface volleyInterface) {
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(params);

        Set<Map.Entry<String, String>> entries = sortMap.entrySet();

        long timestamp = System.currentTimeMillis();

        StringBuffer sb = new StringBuffer();
        sb.append(URL_NAME).append(URL_KEY).append(timestamp);

        for(Map.Entry<String, String> entry : entries) {
            sb.append(entry.getValue());
        }

        String MD5 = MD5Util.encrypt(sb.toString()).toUpperCase();

        params.put(URL_SIGN_KEY, MD5);
        //params.put(URL_NAME_KEY, URL_NAME);
        //params.put(URL_KEY_KEY, URL_KEY);
        params.put(URL_TIMESTAMP_KEY, timestamp+"");

        volleyInterface.syncContext(context);
        MyApplication.getInstance().getRequestQueue().cancelAll(context.getClass().getName());
        JSONObject jsonObject = new JSONObject(params);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, volleyInterface.initJsonObjectListener(), volleyInterface.initErrorLisener());
        jsonObjectRequest.setTag(context.getClass().getSimpleName());
        MyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);

        //if(BuildConfig.DEBUG) {
        if(true) {
            Log.e(TAG, "PostUrl: " + url);
            Log.e(TAG, "PostParams: " + jsonObject.toString());
        }
    }

}


