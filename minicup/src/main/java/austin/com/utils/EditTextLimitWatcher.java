package austin.com.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/3/17.
 */
public class EditTextLimitWatcher implements TextWatcher {
    private EditText et;
    private int num;
    private Context context;

    public EditTextLimitWatcher(EditText et, int num, Context context) {
//        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
        this.et = et;
        this.num = num;
        this.context = context;
        this.et.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.toString().length() > num) {
            Toast.makeText(context, "所输入的内容不能超过" + num + "字", Toast.LENGTH_SHORT).show();
            et.setText(s.toString().substring(0, num));
            et.setSelection(num);
        }

    }

    public static void copy(String content, Context context) {
//得到剪贴板管理器
        ClipboardManager
                cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }
}
