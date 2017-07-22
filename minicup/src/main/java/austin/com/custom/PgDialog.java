package austin.com.custom;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import austin.com.R;
import austin.com.utils.MiniCup;


/**
 * Created by Austin on 2016/11/3.
 */

public class PgDialog {
    private int rscId;
    private String text;
    private boolean canclable;
    private AlertDialog dialog;


    public PgDialog(@NonNull Context context, String text, boolean canclable) {
        this.text = text;
        this.canclable = canclable;
        initDialog(context);
    }


    protected void initDialog(final Context context){
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        View view = LayoutInflater.from(context).inflate(R.layout.progress, null);
        dialog.setCancelable(canclable);
        window.setWindowAnimations(R.style.DialogAnimation);
        window.setContentView(view);

        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = MiniCup.dp2Px(context, 100);   //设置宽度
        dialog.getWindow().setAttributes(p);     //设置生效

        ProgressBar pg;
        TextView text;

        pg = (ProgressBar) view.findViewById(R.id.pg);
        text = (TextView) view.findViewById(R.id.text);
        text.setText(this.text);
    }


    public void dismiss(){
        dialog.dismiss();
    }

    public boolean isShowing() {

        return dialog.isShowing();
    }

    public void myshow() {
        dialog.show();
    }

    public void dismissAfter(int milisecond, @Nullable final DismissCallback callback) {
        if (dialog != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    if(callback!=null)
                        callback.onDismiss();
                }
            }, milisecond);
        }
    }

    public interface DismissCallback{
        void onDismiss();
    }
}
