package austin.com.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Austin on 2016/11/10.
 * 这里不用单例模式，防止多个Activity监听
 * Todo 6.0以上的权限问题
 *
 * 使用方法：
 *  receiver = SMSReceiver getReceiver(MessageListener messageListener)
 *  SMSReceiver.getIntentFilter()
 *
 *  registerReceiver(receiver, SMSReceiver.getIntentFilter());
 *  unregisterReceiver(receiver);
 */
public class SMSReceiver extends BroadcastReceiver {
    private static final String ACTION_SMS_RECEVED = "android.provider.Telephony.SMS_RECEIVED";

    /**
     * 设置截取的Pattern
     */
    public final String PATTERN = "[0-9]{6}";

    private MessageListener messageListener;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_SMS_RECEVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //如果短信内容太长，会自动分割
                Object[] puds = (Object[]) bundle.get("pdus");
                for (Object pud : puds) {
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) pud);
                    String messageBody = message.getMessageBody();
                    //每次获得的发信人是一样的
                    String sender = message.getOriginatingAddress();
                    Pattern pattern = Pattern.compile(PATTERN);
                    Matcher matcher = pattern.matcher(messageBody);
                    //此处只会匹配第一个符合条件的字符串
                    if (matcher.find()) {
                        String code = matcher.group();
                        if (messageListener == null) {
                            throw  new IllegalArgumentException("SMSReceiver need setMessageListener()");
                        }else {
                            messageListener.onGetResult(code);
                        }
                    }
                }
            }
        }
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter(ACTION_SMS_RECEVED);
        intentFilter.setPriority(Integer.MAX_VALUE);
        return intentFilter;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public interface MessageListener {
        void onGetResult(String result);
    }
}
