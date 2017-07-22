package austin.com.listener;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by Austin on 2016/11/10.
 */
public class BackListener implements View.OnClickListener {
    private Context context;

    public BackListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        ((FragmentActivity)context).onBackPressed();
    }
}
