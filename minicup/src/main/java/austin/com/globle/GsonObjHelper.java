package austin.com.globle;

import com.google.gson.Gson;

/**
 * Author:Administrator on 2015/7/30 14:13
 * Email:edison_ctj@sina.cn
 * ToDo:JSON解析帮助
 */
public class GsonObjHelper {
    public static Gson gson = new Gson();
    public static <T> T getEntity(String s, Class<T> clazz) {
        return gson.fromJson(s, clazz);
    }
}
