package austin.com.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/26.
 */
public class JsonUtil {
    /**
     * 判断是否是json
     * @param str
     * @return
     */
    public static boolean isJSon(String str) {
        try {
            JSONObject object = new JSONObject(str);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Map<String, Object> getMaps(String ob) {
        try {
            return getMap(ob);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param object
     * @return
     * @throws JSONException
     */
    public static Map<String, Object> getMap(String object) throws JSONException {
        JSONObject json;
        if (TextUtils.isEmpty(object)) {
            json = new JSONObject();
        } else {
            json = new JSONObject(object);
        }
        return getMap(json);
    }

    /**
     * 由Json数据获取Map<String,Object>
     *
     * @param object
     * @return
     * @throws JSONException
     */
    public static Map<String, Object> getMap(JSONObject object) throws JSONException {
        Map<String, Object> objectMap = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            Object o = object.get(next);
            if (o instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) o;
                List<Object> mapList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mapList.add(getMap(jsonObject));
                    } catch (JSONException e) {
                        mapList.add(jsonArray.get(i).toString());
                    }
                }
                objectMap.put(next, mapList);
            } else if (o instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) o;
                objectMap.put(next, getMap(jsonObject));
            } else {
                objectMap.put(next, o);
            }
        }
        return objectMap;
    }
}
