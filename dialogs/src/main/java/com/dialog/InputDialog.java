package com.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.austin.passworddialog.R;


/**
 *
 *  原生AlertDialog使用方法,注意指定Theme，防止键盘遮挡。指定softinputmode，自动弹出软键盘
 *
 *  EditText editText = new EditText(MainActivity.this);
    editText.setBackgroundDrawable(null);
    editText.setGravity(Gravity.CENTER);

    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this,R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setView(editText)
                        .setTitle("Title")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                            })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
 *
 *
 */
public class InputDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private EditText txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public InputDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public InputDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.input_dialog, null);

        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);

        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);

        txt_msg = (EditText) view.findViewById(R.id.et_msg);
        txt_msg.setBackgroundDrawable(null);
        txt_msg.setVisibility(View.GONE);

        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);

        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setTag(txt_msg);
        btn_pos.setVisibility(View.GONE);

        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        //自定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        //调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));

//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
        return this;
    }

    public InputDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public InputDialog setTextHint(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setHint("");
        } else {
            txt_msg.setHint(msg);
        }
        return this;
    }

    public InputDialog setTextInputType(int type) {
        txt_msg.setInputType(type);
        return this;
    }
    public InputDialog setText(String msg) {
        showMsg = true;
        if (TextUtils.isEmpty(msg)) {
            txt_msg.setText("");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }


    public InputDialog setCanceledOnTouchOutside(boolean cancelable) {
        dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public InputDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public InputDialog setPositiveButton(String text,
                                         final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public InputDialog setNegativeButton(String text,
                                         final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public InputDialog setOnCancelListent(DialogInterface.OnCancelListener listent) {
        dialog.setOnCancelListener(listent);
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        if (dialog != null)
            dialog.show();
    }


}
