package austin.com.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

import austin.com.http.VolleyInterface;

/**
 * Created by Austin on 2017/7/16.
 */

public class UpdateUtil {

    private static Context mContext;
    private static UpdateUtil instance;

    private UpdateUtil(Context context) {
        mContext = context;
    }

    public static UpdateUtil with(Context context) {
        if (instance == null) {
            synchronized (UpdateUtil.class) {
                if (instance == null) {
                    instance = new Builder(context).build();
                }
            }
        }
        return instance;
    }

    public UpdateExcecutor url(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("Please set download Url");
        }
        return new UpdateExcecutor(mContext, this, url);
    }


    public static class Builder {
        private final Context context;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context;
        }

        public UpdateUtil build() {
            return new UpdateUtil(context);
        }

    }

    public class UpdateExcecutor {

        private Context mContext;
        private final UpdateUtil updateUtil;
        private final String url;
        private String targetPath;
        private int method;
        private Map<String, Object> params;
        private Class responseClz;
        private VolleyInterface vi;

        public UpdateExcecutor(Context mContext, UpdateUtil updateUtil, String url) {
            this.mContext = mContext;
            this.updateUtil = updateUtil;
            this.url = url;
        }

        public UpdateExcecutor into(String targetPath) {
            this.targetPath = targetPath;
            downLoadAPK();
            return this;
        }

        public void downLoadAPK() {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                throw new IllegalArgumentException("No Permission WRITE_EXTERNAL_STORAGE");
            }

            final ProgressDialog pd;    //进度条对话框
            pd = new ProgressDialog(mContext);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMessage("正在下载更新");
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(false);
            pd.show();
            new Thread() {
                @Override
                public void run() {
                    try {
                        File file = DownLoadManager.getFileFromServer(url, pd, targetPath);
                        sleep(1000);
                        installApk(file);
                        pd.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "下载新版本失败", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        });
                    }
                }
            }.start();
        }

        protected void installApk(File file) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            mContext.startActivity(intent);
        }

    }


}
