package com.austin.universallibrary;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.dialog.InputDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yu.pay.PayFragment;

import java.util.ArrayList;
import java.util.List;

import austin.com.activity.base.TitleBarActivity;
import austin.com.fragments.WebFragment;
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
    private LinearLayout mWebViewContainer;
    private WebFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        List<Transformation> list = new ArrayList<>();
        list.add(new CircleTransformation());
        list.add(new BlurTransformation());
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load("http://i6.qhimg.com/t015dbfc00a77e3911e.jpg")
                .transform(list).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* EditText editText = new EditText(MainActivity.this);
                editText.setBackgroundDrawable(null);
                editText.setGravity(Gravity.CENTER);

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this,R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setView(editText)
                        .setTitle("Title")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);*/


                new InputDialog(MainActivity.this)
                        .builder()
                        .setTitle("title")
                        .setTextHint("hint")
                        .setTextInputType(InputType.TYPE_CLASS_NUMBER)
                        .setText("text")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();

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



        mWebViewContainer = (LinearLayout) findViewById(R.id.webViewContainer);

        fragment = WebFragment.newInstance("http://www.baidu.com");
        getSupportFragmentManager().beginTransaction().add(R.id.webViewContainer, fragment, "web").commit();

    }

    @Override
    public void onBackPressed() {
        if(fragment.onBackPressed()){
        }else{
            super.onBackPressed();
        }
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
