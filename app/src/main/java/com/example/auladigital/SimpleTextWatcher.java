package com.example.auladigital;

import android.text.Editable;
import android.text.TextWatcher;

public class SimpleTextWatcher implements TextWatcher {
    private final OnTextChangedListener listener;

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }

    public SimpleTextWatcher(OnTextChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No hacemos nada antes de que cambie el texto
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listener.onTextChanged(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        // No hacemos nada después que cambió el texto
    }
}
