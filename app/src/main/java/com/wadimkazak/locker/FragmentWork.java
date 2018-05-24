package com.wadimkazak.locker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class FragmentWork extends Fragment {
    Button btnLock, btnUnlock;
    EditText editTextWithMainText, editTextWithKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workplace, container, false);
        editTextWithMainText = v.findViewById(R.id.et_text_work_space);
        editTextWithKey = v.findViewById(R.id.editTextForKey);
        btnLock = v.findViewById(R.id.btn_lock);
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkText workText = WorkText.get(editTextWithMainText.getText().toString(), editTextWithKey.getText().toString());
                workText.lockText();
                editTextWithMainText.setText(workText.getText());


            }
        });

        btnUnlock = v.findViewById(R.id.btn_unlock);
        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkText workText = WorkText.get(editTextWithMainText.getText().toString(), editTextWithKey.getText().toString());
                workText.unlockText();
                editTextWithMainText.setText(workText.getText());
            }
        });

        return v;
    }
}
