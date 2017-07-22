package austin.com.utils;

import android.content.Context;

import austin.com.MyApplication;


public class SharePreferenceUtils extends SharePreferenceBase {


    static String SP_FILE_NAME = "smartdriver";
    String SP_PHONE_NUM = "SP_PHONE_NUM";
    String SP_USERNAME = "SP_USERNAME";
    String SP_PASSWORD = "SP_PASSWORD";



    private static SharePreferenceUtils preferenceUtils;

    private SharePreferenceUtils(Context context) {
        super(context, SP_FILE_NAME);
    }  
  
    public synchronized static SharePreferenceUtils getInstance() {
        if (null == preferenceUtils) {  
            preferenceUtils = new SharePreferenceUtils(MyApplication.getInstance());
        }  
        return preferenceUtils;  
    }


    public void savePhoneNum(String phone) {
        setString(SP_PHONE_NUM, phone);
    }

    public String getPhoneNum(){
        return getString(SP_PHONE_NUM);
    }

    public void savePassword(String pwd) {
        setString(SP_PASSWORD, pwd);
    }

    public String getPassword() {
        return getString(SP_PASSWORD);
    }
}