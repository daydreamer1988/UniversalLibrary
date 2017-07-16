package austin.com.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import austin.com.BuildConfig;
import austin.com.globle.GsonObjHelper;

/**
 * Created by Austin on 2016/10/28.
 */

public abstract class VolleyInterface<T> {
    private Context context;
    private Class clazz;
    private String requestTag;


    public VolleyInterface(Class<T> clazz, String requestTag) {
        this.clazz = clazz;
        this.requestTag = requestTag;
    }


    public abstract void onSuccess(T response);

    public abstract void onFail(VolleyError error);


    public Listener<String> initStringLisener(){
        Listener<String> listener = new Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(BuildConfig.DEBUG) {
                    Log.e(context.getClass().getSimpleName() + " ---onResponse: " + requestTag, s);
                }
                onSuccess((T) s);
            }
        };
        return listener;
    }

    public Listener<JSONObject> initJsonObjectListener(){
        Listener<JSONObject> listener = new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                T t = null;
                if(BuildConfig.DEBUG) {
                    Log.e(context.getClass().getSimpleName() + " ---onResponse: " + requestTag, jsonObject.toString());
                }
                t = (T) GsonObjHelper.getEntity(jsonObject.toString(), clazz);
                onSuccess(t);
            }
        };
        return listener;
    }

    public Listener<JSONArray> initArrayLisener(){
        Listener<JSONArray> listener = new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray s) {
                T t = null;
                if(BuildConfig.DEBUG) {
                    Log.e(context.getClass().getSimpleName() + " ---onResponse: " + requestTag, s.toString());
                }
                t = (T)s;
                onSuccess(t);
            }
        };
        return listener;
    }


    public ErrorListener initErrorLisener(){
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(BuildConfig.DEBUG) {
                    Log.e(context.getClass().getSimpleName() + " ---onFail: " + requestTag, volleyError.toString());
                }
                onFail(volleyError);
            }
        };
        return errorListener;
    }






    public void syncContext(Context ct) {
        this.context = ct;
    }
}
