package com.austin.universallibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import austin.com.activity.base.TitleBarActivity;
import austin.com.bean.response.VersionResp;
import austin.com.http.ApiManager;
import austin.com.http.VolleyInterface;
import austin.com.utils.InputMethodUtil;
import austin.com.utils.NetWorkUtil;
import austin.com.utils.UpdateUtil;

public class MainActivity extends TitleBarActivity {
    private LinearLayout mActivityMain;
    private EditText mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mText = (EditText) findViewById(R.id.text);
        mActivityMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodUtil.hideSoftInput(v);


                if (!NetWorkUtil.checkNetworkConnection(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "no internet", Toast.LENGTH_SHORT).show();
                }else {
                    ApiManager.checkVersion(MainActivity.this, new VolleyInterface<VersionResp>(VersionResp.class, "检查版本更新") {
                        @Override
                        public void onSuccess(VersionResp response) {
                            if (response != null && response.getError() == 1) {
                                UpdateUtil.with(MainActivity.this).url(response.getVersionurl()).into(APP_FILE_ROOT_PATH);
                            }
                        }

                        @Override
                        public void onFail(VolleyError error) {
                        }
                    });


                }
            }
        });




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
}
