package austin.com.activity.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import austin.com.MyApplication;
import austin.com.custom.PgDialog;
import austin.com.globle.Appconstants;


/**
 * Created by Austin on 2016/10/28.
 */

public class BaseActivity extends AppCompatActivity implements Appconstants {

    private PgDialog pgDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getApplication()).addActity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getInstance().getRequestQueue().cancelAll(this.getClass().getSimpleName());
    }

    public void dismissDialog() {
        if (pgDialog != null && pgDialog.isShowing()) {
            pgDialog.dismiss();
        }
    }

    public void showPgDialog(String text) {
        if (pgDialog == null) {
            pgDialog = new PgDialog(this, text, false);
        }else{
            pgDialog.myshow();
        }
    }
}
