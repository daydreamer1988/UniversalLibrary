package austin.com.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import austin.com.bean.response.VersionResp;
import austin.com.globle.Appconstants;
import austin.com.globle.UrlConstants;
import austin.com.utils.NetWorkUtil;

/**
 * Created by Austin on 2016/10/28.
 */

public class ApiManager implements Appconstants {


    public static void baiduTest(Context context, VolleyInterface volleyInterface) {
        if (NetWorkUtil.checkNetworkConnection(context)) {
            String url = UrlConstants.BAIDUURL;
            Map<String, String> params = new HashMap<>();
            RequestUtils.GetStringRequest(context, url, params, volleyInterface);
        }
    }

    public static void phpTest(Context context, VolleyInterface vi) {
        if (NetWorkUtil.checkNetworkConnection(context)) {
            String url = UrlConstants.BAIDUURL;
            Map<String, String> params = new HashMap<>();
            params.put("name", "austin");
            params.put("age", "3");
            RequestUtils.GetStringRequest(context, url, params, vi);
        }
    }

    public static void checkVersion(Context context, VolleyInterface<VersionResp> volleyInterface) {
        if (NetWorkUtil.checkNetworkConnection(context)) {
            String url = "http://api.dikechina.cn/App/Index/Version1";
            Map<String, String> params = new HashMap<>();
            params.put("UserId", "35");
            RequestUtils.PostJsonObjectRequest(context, url, params, volleyInterface);
        }
    }

}
