package com.austin.universallibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yu.pay.PayFragment;
import com.yu.pay.PayPasswordView;

import austin.com.activity.base.TitleBarActivity;
import austin.com.custom.PgDialog;
import austin.com.http.ApiManager;
import austin.com.http.VolleyInterface;
import austin.com.receivers.SMSReceiver;
import austin.com.receivers.SMSReceiver2;
import austin.com.utils.AllCapTransformationMethod;
import austin.com.utils.UpdateUtil;

public class MainActivity extends TitleBarActivity {
    private LinearLayout mActivityMain;
    private EditText mText;
    private PayFragment mPayFragment;
    private SMSReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mText = (EditText) findViewById(R.id.text);
        mActivityMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PayFragment.EXTRA_CONTENT, "" + 100.00);
                mPayFragment = new PayFragment();
                mPayFragment.setArguments(bundle);
                mPayFragment.setInputSucessCallBack(new PayPasswordView.InputCallBack() {
                    @Override
                    public void onInputFinsh(String result) {
                        mPayFragment.dismiss();
                        Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                    }
                });
                mPayFragment.show(getSupportFragmentManager(), "pay");
            }
        });

        new PgDialog(MainActivity.this, "fuck", false).dismissAfter(2000, new PgDialog.DismissCallback() {
            @Override
            public void onDismiss() {
                Toast.makeText(MainActivity.this, "fuck done", Toast.LENGTH_SHORT).show();
            }
        });

        /*receiver =  new SMSReceiver();
        receiver.setMessageListener(new SMSReceiver.MessageListener() {
            @Override
            public void onGetResult(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
        registerReceiver(receiver, receiver.getIntentFilter());*/

        SMSReceiver2.registerReceiver(this).setMessageListener(new SMSReceiver2.MessageListener() {
            @Override
            public void onGetResult(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });

        mText.setTransformationMethod(new AllCapTransformationMethod());



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSReceiver2.unRegisterReceiver(this);
    }

    public void doClick(View view) {
        ApiManager.baiduTest(MainActivity.this, new VolleyInterface<String>(String.class, "phpTest") {
            @Override
            public void onSuccess(String response) {
                mText.setText(response);
            }

            @Override
            public void onFail(VolleyError error) {

            }
        });
    }

    @Override
    public View.OnClickListener onMyBackPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    @Override
    public String setTitle() {
        return "Main";
    }
}
