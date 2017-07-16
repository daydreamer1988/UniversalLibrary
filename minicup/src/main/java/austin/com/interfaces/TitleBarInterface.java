package austin.com.interfaces;

import android.view.View;

/**
 * Created by Austin on 2016/11/9.
 */

public interface TitleBarInterface {

    View.OnClickListener onMyBackPressed();

    String setTitle();

    String setRightTitle();

    View.OnClickListener onRightTextClicked();

    int setRightImage();
}
