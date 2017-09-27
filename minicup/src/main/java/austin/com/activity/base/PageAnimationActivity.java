package austin.com.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import austin.com.R;
import austin.com.custom.PgDialog;
import austin.com.globle.Appconstants;


/**
 * Created by Austin on 2016/10/28.
 */

public class PageAnimationActivity extends BaseActivity implements Appconstants {
    private PgDialog pgDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 设置全局启动动画
     */
//    @Override
//    public void startActivity(Intent intent) {
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        super.startActivity(intent);
//        overridePendingTransition(R.anim.act_dync_in_from_right, R.anim.act_dync_out_to_left);
//    }

    /**
     * 设置全局退出动画
     */
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.act_dync_in_from_left, R.anim.act_dync_out_to_right);
//    }

    /**
     * 设置全局退出动画（物理键）
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            boolean b = super.onKeyDown(keyCode, event);
//            overridePendingTransition(R.anim.act_dync_in_from_left, R.anim.act_dync_out_to_right);
//            return b;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    public void startActivityWithAnimation(Intent intent, int inanim, int outanim) {
        startActivity(intent);
        overridePendingTransition(inanim, outanim);
    }

    public void startActivityForResultWithAnimation(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.act_dync_in_from_right, R.anim.act_dync_out_to_left);
    }

    public void startActivityForResultWithAnimation(Intent intent, int requestCode, int inanim, int outanim) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(inanim, outanim);
    }

    public void finishActivity(int inanim, int outanim) {
        finish();
        overridePendingTransition(inanim, outanim);
    }


}
