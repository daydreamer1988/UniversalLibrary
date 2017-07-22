package austin.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceBase {
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    protected SharePreferenceBase(Context context, String fileName) {
        this.context = context;
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }  
  
    protected void setString(String key, String value) {
        editor.putString(key, value).apply();
    }  
  
    protected String getString(String key) {  
        return sp.getString(key, "");
    }  
  
    protected void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }  
  
    protected boolean getBoolean(String key) {  
        return sp.getBoolean(key, false);  
    }  
  
    protected void setInt(String key, int value) {
        editor.putInt(key, value).apply();
    }  
  
    protected int getInt(String key) {  
        return sp.getInt(key, 0);  
    }  
}  