package com.woodys.demos.widget;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.cz.library.widget.editlayout.IEditText;
import com.woodys.demos.R;

/**
 * Created by cz on 11/3/16.
 */
public class MyEditInputLayout extends TextInputLayout implements IEditText {
    public EditText editor;
    public MyEditInputLayout(Context context) {
        this(context,null,0);
    }

    public MyEditInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyEditInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context,  R.layout.edit_view,this);
        editor= (EditText) findViewById(R.id.et_editor);
    }

    public EditText getEditor() {
        return editor;
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
        editor.setOnFocusChangeListener(l);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        editor.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        editor.removeTextChangedListener(watcher);
    }

    @Override
    public void setTextColor(int color) {
        editor.setTextColor(color);
    }

    @Override
    public Editable getText() {
        return editor.getText();
    }

    @Override
    public void setText(CharSequence text) {
        editor.setText(text);
    }

    @Override
    public void setSelection(int selection) {
        editor.setSelection(selection);
    }

    @Override
    public void setHintTextColor(int color) {
        editor.setHintTextColor(color);
    }

    @Override
    public void setTextSize(int type, float textSize) {
        editor.setTextSize(type,textSize);
    }

    @Override
    public void setInputType(int inputType) {
        editor.setInputType(inputType);
    }

    @Override
    public void setTransformationMethod(TransformationMethod method) {
        editor.setTransformationMethod(method);
    }

    @Override
    public TransformationMethod getTransformationMethod() {
        return null;
    }

    @Override
    public void setFilters(InputFilter[] filters) {
        editor.setFilters(filters);
    }

    @Override
    public void setMaxLines(int maxLines) {
        editor.setMaxLines(maxLines);
    }
}
