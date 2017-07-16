package austin.com.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import austin.com.bean.response.VersionResp;
import austin.com.globle.Appconstants;
import austin.com.globle.UrlConstants;

/**
 * Created by Austin on 2016/10/28.
 */

public class ApiManager implements Appconstants {


    public static void baiduTest(Context context, VolleyInterface volleyInterface) {
        String url = UrlConstants.BAIDUURL;
        Map<String, String> params = new HashMap<>();
        RequestUtils.getStringRequest(context,url, params, volleyInterface);
    }

    public static void phpTest(Context context, VolleyInterface vi) {
        String url = UrlConstants.BAIDUURL;
        Map<String, String> params = new HashMap<>();
        params.put("name", "austin");
        params.put("age", "3");
        RequestUtils.getStringRequest2(context,url, params, vi);
    }

    public static void checkVersion(Context context, VolleyInterface<VersionResp> volleyInterface) {
        String url = "http://api.dikechina.cn/App/Index/Version1";
        Map<String, String> params = new HashMap<>();
        params.put("UserId", "35");
        RequestUtils.postJsonRequest(context, url, params, volleyInterface);
    }

}
