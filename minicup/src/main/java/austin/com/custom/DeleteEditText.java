package austin.com.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class DeleteEditText extends EditText {

    private Drawable dLeft;
    private Drawable dRight;
    private Rect rBounds;


    public DeleteEditText(Context paramContext) {
        super(paramContext);
        initEditText();
    }

    public DeleteEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initEditText();
    }

    public DeleteEditText(Context paramContext,
                          AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);

        initEditText();
    }


    private void initEditText() {
        setEditTextDrawable();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable paramEditable) {

            }

            @Override
            public void beforeTextChanged(CharSequence paramCharSequence,
                                          int paramInt1, int paramInt2, int paramInt3) {
            }

            @Override
            public void onTextChanged(CharSequence paramCharSequence,
                                      int paramInt1, int paramInt2, int paramInt3) {
                DeleteEditText.this.setEditTextDrawable();
            }
        });
    }

    public void setEditTextDrawable() {
        if (getText().toString().length() == 0) {
            setCompoundDrawables(this.dLeft, null, null, null);
        } else {
            setCompoundDrawables(this.dLeft, null, this.dRight, null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dLeft = null;
        this.dRight = null;
        this.rBounds = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if ((this.dRight != null) && (motionEvent.getAction() == 1)) {
            this.rBounds = this.dRight.getBounds();
            int i = (int) motionEvent.getRawX(); // to screen
            // int i = (int) motionEvent.getX(); // to bound
            if (i > getRight() - 3 * this.rBounds.width()) {
                setText("");
                motionEvent.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void setCompoundDrawables(Drawable paramDrawable1,
                                     Drawable paramDrawable2, Drawable paramDrawable3,
                                     Drawable paramDrawable4) {
        if (paramDrawable1 != null) {
            this.dLeft = paramDrawable1;
        }
        if (paramDrawable3 != null) {
            this.dRight = paramDrawable3;
        }
        super.setCompoundDrawables(paramDrawable1, paramDrawable2,
                paramDrawable3, paramDrawable4);
    }


}