package austin.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import austin.com.globle.Appconstants;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Austin on 2017/7/15.
 */

public class SharedPreferencesUtils {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        sp = context.getSharedPreferences(Appconstants.APP_SP, MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SharedPreferences.Editor getEditor() {
        return editor;
    }

    public static SharedPreferences getSp() {
        return sp;
    }


    public static void updateSp(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void updateSp(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

}
