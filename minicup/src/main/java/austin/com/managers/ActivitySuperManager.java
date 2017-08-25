package austin.com.managers;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by gy on 2017/8/21.
 */

public class ActivitySuperManager {
    /**
     * get top activity
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return runningTaskInfos.get(0).topActivity.getClassName();
        else
            return "";
    }
}
