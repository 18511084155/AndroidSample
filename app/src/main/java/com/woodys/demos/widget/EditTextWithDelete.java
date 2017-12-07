package com.woodys.demos.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.woodys.demos.R;

public class EditTextWithDelete extends EditText implements OnFocusChangeListener {

    private int mDeleteIcon;
    private Drawable mDeleteIconDrawable;

    private EditTextWithDeleteTouchInterface deleteAfterListener;
    private TextChangedListener textChangedListener;

    public interface EditTextWithDeleteTouchInterface {
        void deleteAfter();
    }

    public interface TextChangedListener{
        void onTextChanged(CharSequence s);
    }

    public void setTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    public EditTextWithDelete(Context context) {
        this(context, null);
    }

    public EditTextWithDelete(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextWithDelete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {
            TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithDelete);
            //设置返回的图片-有的时候是X
            mDeleteIcon = tArray.getResourceId(R.styleable.EditTextWithDelete_deleteIcon, -1);
            if (mDeleteIcon > 0) {
                mDeleteIconDrawable = context.getResources().getDrawable(tArray.getResourceId(R.styleable.EditTextWithDelete_deleteIcon, R.drawable.close_icon));
            }
            tArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    //输入框中删除信息的图片按钮
    private void init() {
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setOnFocusChangeListener(this);
        addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textChangedListener != null) {
                    textChangedListener.onTextChanged(s);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });

        setDrawable();
    }

    /**
     * 设置删除图片
     */
    private void setDrawable() {
        if (mDeleteIconDrawable == null) {
            return;
        }
        if (length() == 0 || !this.isFocused()) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDeleteIconDrawable, null);
        }
    }

    /**
     * event.getX() 获取相对应自身左上角的X坐标 event.getY() 获取相对应自身左上角的Y坐标 getWidth()
     * 获取控件的宽度 getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离 getPaddingRight()
     * 获取删除图标右边缘到控件右边缘的距离 getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     * getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDeleteIconDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            // 获取删除图标的边界，返回一个Rect对象
            Rect rect = mDeleteIconDrawable.getBounds();
            // 获取删除图标的宽度
            int width = rect.width();
            // 获取删除图标的高度
            int height = rect.height();

            int x = (int) event.getX();
            // 判断触摸点是否在水平范围内
            boolean isInnerWidth = (x > (getWidth() - getTotalPaddingRight()))
                    && (x < (getWidth() + width));

            int y = (int) event.getY();
            // 计算图标底部到控件底部的距离
            int distance = (getHeight() - height) / 2;
            // 判断触摸点是否在竖直范围内(可能会有点误差)
            // 触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
            boolean isInnerHeight = (y > 0) && (y < getHeight());

            if (isInnerWidth && isInnerHeight) {
                setText("");
                //联动
                if (deleteAfterListener != null) {
                    deleteAfterListener.deleteAfter();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && isEnabled()) {
            setDrawable();
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    public void setDeleteAfterListener(EditTextWithDeleteTouchInterface deleteAfterListener) {
        this.deleteAfterListener = deleteAfterListener;
    }
}
