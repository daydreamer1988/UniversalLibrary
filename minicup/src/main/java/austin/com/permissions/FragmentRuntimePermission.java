package austin.com.permissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by gy on 2017/7/25.
 */

public class FragmentRuntimePermission {

    public static String CAMARA = Manifest.permission.CAMERA;
    public static String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final int START_SETTING = 95;

    private static final int CAMARA_REQUEST_CODE = 415;
    private static final int LOCATION_REQUEST_CODE = 248;
    private static final int STORAGE_REQUEST_CODE = 802;
    private static final int OTHER_REQUEST_CODE = 248;

    private RuntimePermissionCallback callback;
    private FragmentActivity context;
    private Fragment fragment;
    private String permission;
    private int startSettingRequestCode;


    public void checkPermissions(Fragment fragment, String permission) {
        this.context = fragment.getActivity();
        this.fragment = fragment;
        this.permission = permission;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                if(permission.equals(CAMARA)) {
                    fragment.requestPermissions(new String[]{permission}, CAMARA_REQUEST_CODE);
                } else if (permission.equals(LOCATION)) {
                    fragment.requestPermissions(new String[]{permission}, LOCATION_REQUEST_CODE);
                } else if(permission.equals(STORAGE)){
                    fragment.requestPermissions(new String[]{permission}, STORAGE_REQUEST_CODE);
                } else {
                    fragment.requestPermissions(new String[]{permission}, OTHER_REQUEST_CODE);
                }
            } else {
                setOnGranted();
            }
        }else{
            setOnGranted();
        }
    }


    public void setRuntimePermissionCallback(RuntimePermissionCallback callback){
        this.callback = callback;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions.length > 0 && permissions[0].equals(permission)) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setOnGranted();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    setOnShowRational();
                } else {
                    setOnNeverAsk();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == startSettingRequestCode) {
            checkPermissions(fragment, permission);
        }
    }


    /**
     * 在onShowRational回调中可以使用该方法，提供一个通用简单的AlertDialog
     * @param message
     */
    public void showRationalDialog(String message) {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setOnDismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermissions(fragment, permission);
                    }
                })
                .show();
    }



    /**
     * 在onNeverAsk回调中可以使用该方法，提供一个通用简单的AlertDialog
     * @param message
     */
    public void showNeverAskDialog(String message, final int requestCode) {
        startSettingRequestCode = requestCode;
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setOnDismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                        context.startActivityForResult(intent, requestCode);
                    }
                })
                .show();
    }

    private void setOnNeverAsk() {
        if(callback!=null){
            callback.onNeverAsk();
        }else{
            throw new IllegalArgumentException("需要setRuntimePermissionCallback");
        }
    }

    private void setOnShowRational() {
        if(callback!=null){
            callback.onShowRational();
        }else{
            throw new IllegalArgumentException("需要setRuntimePermissionCallback");
        }
    }
    private void setOnDismiss() {
        if(callback!=null){
            String dissmissString = callback.onDismiss();
            if(!TextUtils.isEmpty(dissmissString))
            Toast.makeText(context, dissmissString, Toast.LENGTH_SHORT).show();
        }else{
            throw new IllegalArgumentException("需要setRuntimePermissionCallback");
        }
    }

    private void setOnGranted() {
        if(callback!=null){
            callback.onGranted();
        }
    }


}
