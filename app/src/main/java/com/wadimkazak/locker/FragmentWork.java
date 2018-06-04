package com.wadimkazak.locker;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentWork extends Fragment {
    Button btnLock, btnUnlock, btnClear;
    EditText editTextWithMainText, editTextWithKey;
    TextView tvCounter;
    private final String PATH_FONT = "fonts/8289.otf";
    private final String TYPE_INTENT = "text/plain";


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_work_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(TYPE_INTENT);
        intent.putExtra(Intent.EXTRA_TEXT, editTextWithMainText.getText().toString());
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workplace, container, false);
        setHasOptionsMenu(true);

        editTextWithMainText = v.findViewById(R.id.et_text_work_space);
        editTextWithKey = v.findViewById(R.id.editTextForKey);
        tvCounter = v.findViewById(R.id.tv_counter_keychar);
        btnLock = v.findViewById(R.id.btn_lock);
        btnUnlock = v.findViewById(R.id.btn_unlock);
        btnClear = v.findViewById(R.id.btn_clear);

        btnUnlock.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), PATH_FONT));
        editTextWithKey.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), PATH_FONT));
        editTextWithMainText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), PATH_FONT));
        btnLock.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), PATH_FONT));
        btnClear.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), PATH_FONT));


        editTextWithKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCounter.setText(String.valueOf(charSequence.length()));

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextWithMainText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.enter_text), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextWithKey.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), R.string.enter_key, Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("TT", String.valueOf(editTextWithKey.getText().toString().hashCode()));
                WorkText workText = WorkText.get();
                workText.setText(editTextWithMainText.getText().toString());
                workText.setKey(editTextWithKey.getText().toString());
                workText.setContext(getActivity());
                workText.lockText();
                editTextWithMainText.setText(workText.getText());
                workText.setText("");


            }
        });


        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextWithMainText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.enter_text), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextWithKey.getText().toString().equals("")) {
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

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextWithMainText.setText("");
                WorkText.COUNT_OF_ENCODINGS = 0;

            }
        });

        return v;
    }
}
