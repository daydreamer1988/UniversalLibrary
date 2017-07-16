package austin.com.utils;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Austin on 2017/7/12.
 */
public class InputMethodUtil {
    public static void hideSoftInput(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        boolean active = manager.isActive();
        if (active) {
            manager.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    public static void showInputMethod(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        boolean active = manager.isActive();
        if (!active) {
            manager.showSoftInput(v, 0);
        }
    }
}
