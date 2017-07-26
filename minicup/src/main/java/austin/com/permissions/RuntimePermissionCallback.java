package austin.com.permissions;

/**
 * Created by gy on 2017/7/25.
 */
public interface RuntimePermissionCallback{
    void onGranted();
    void onShowRational();
    void onNeverAsk();
    String onDismiss();
}