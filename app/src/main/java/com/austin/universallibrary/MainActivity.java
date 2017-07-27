package com.austin.universallibrary;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.apple.alert.AlertControl;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yu.pay.PayFragment;

import java.util.ArrayList;
import java.util.List;

import austin.com.activity.base.TitleBarActivity;
import austin.com.http.ApiManager;
import austin.com.http.VolleyInterface;
import austin.com.permissions.RuntimePermission;
import austin.com.receivers.SMSReceiver;
import austin.com.utils.AllCapTransformationMethod;
import austin.com.utils.MiniCup;
import austin.com.utils.PicassoTransform;

public class MainActivity extends TitleBarActivity {
    private LinearLayout mActivityMain;
    private EditText mText;
    private PayFragment mPayFragment;
    private SMSReceiver receiver;
    private RuntimePermission permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Transformation> list = new ArrayList<>();
        list.add(new CircleTransformation());
        list.add(new BlurTransformation());
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load("http://i6.qhimg.com/t015dbfc00a77e3911e.jpg")
                .transform(list).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertControl(MainActivity.this)
                        .setTitle("fuck")
                .setMsg("message")
                .setPositiveButton("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setNegativeButton("no", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setCancelable(false)
                .show();
            }
        });


        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mText = (EditText) findViewById(R.id.text);
        mActivityMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mText.setTransformationMethod(new AllCapTransformationMethod());
        MiniCup.moveCursorToEnd(mText);


    }


    class CircleTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            return PicassoTransform.getCircleTransform(source);
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    class BlurTransformation implements Transformation {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public Bitmap transform(Bitmap source) {
            return PicassoTransform.getBlurBitmap(MainActivity.this, source);
        }

        @Override
        public String key() {
            return "circle";
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
