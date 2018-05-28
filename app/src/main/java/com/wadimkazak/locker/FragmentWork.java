package com.wadimkazak.locker;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentWork extends Fragment {
    Button btnLock, btnUnlock;
    EditText editTextWithMainText, editTextWithKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workplace, container, false);
        editTextWithMainText = v.findViewById(R.id.et_text_work_space);
        editTextWithKey = v.findViewById(R.id.editTextForKey);
        editTextWithKey.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/8289.otf"));
        editTextWithMainText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/8289.otf"));
        btnLock = v.findViewById(R.id.btn_lock);
        btnLock.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/8289.otf"));
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextWithMainText.getText().toString().equals("")){
                    Toast.makeText(getActivity(), getString(R.string.enter_text), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextWithKey.getText().toString().equals("")){
                    Toast.makeText(getActivity(), R.string.enter_key, Toast.LENGTH_SHORT).show();
                    return;
                }
                WorkText workText = WorkText.get();
                workText.setText(editTextWithMainText.getText().toString());
                workText.setKey(editTextWithKey.getText().toString());
                workText.setContext(getActivity());
                workText.lockText();
                editTextWithMainText.setText(workText.getText());
                workText.setText("");


            }
        });

        btnUnlock = v.findViewById(R.id.btn_unlock);
        btnUnlock.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/8289.otf"));
        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextWithMainText.getText().toString().equals("")){
                    Toast.makeText(getActivity(), getString(R.string.enter_text), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextWithKey.getText().toString().equals("")){
                    Toast.makeText(getActivity(), R.string.enter_key, Toast.LENGTH_SHORT).show();
                    return;
                }
                WorkText workText = WorkText.get();
                workText.setText(editTextWithMainText.getText().toString());
                workText.setKey(editTextWithKey.getText().toString());
                workText.setContext(getActivity());
                workText.unlockText();
                editTextWithMainText.setText(workText.getText());
                workText.setText("");
            }
        });

        return v;
    }
}
