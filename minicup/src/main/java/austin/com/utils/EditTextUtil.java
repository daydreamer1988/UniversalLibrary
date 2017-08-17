package austin.com.utils;

import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.widget.TextView;

/**
 * Created by gy on 2017/8/17.
 */

public class EditTextUtil {

    public static void setMaxLengthAndDigits(TextView textView, int length, String digits) {
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        textView.setKeyListener(DigitsKeyListener.getInstance(digits));
    }
}
