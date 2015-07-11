package com.mackenzie_o.simpleciphers.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mackenzie_o.simpleciphers.MainActivity;
import com.mackenzie_o.simpleciphers.R;
import com.mackenzie_o.simpleciphers.ciphers.Caesar;

/**
 * Created by MacKenzie_2 on 1/3/2015.
 */
public class CaesarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragment = inflater.inflate(R.layout.caesar_fragment, container, false);
        EditText shiftEntry = (EditText) fragment.findViewById(R.id.shiftNum);
        shiftEntry.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                String input;
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    input= v.getText().toString();
                    updateShiftIndicator(input, fragment);
                    return true;
                }
                return false;
            }
        });
        shiftEntry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String input;
                EditText editText;

                if (!hasFocus) {
                    editText = (EditText) v;
                    input = editText.getText().toString();
                    updateShiftIndicator(input, fragment);
                }
            }
        });
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);

    }

    public static void updateShiftIndicator(String in, View fragment){
        int num;
        if(in.equals("")){
            num=1;
        }else{
            num = Integer.parseInt(in);
        }
        ToggleButton mode = (ToggleButton) fragment.findViewById(R.id.caesarModeToggle);
        CharSequence updatedText = "\tA -> "+ Caesar.getChar('A', num, true, false, !(mode.isChecked()));
        TextView shiftIndicator = (TextView) fragment.findViewById(R.id.shiftIndicator);
        shiftIndicator.setText(updatedText);

    }
    
}
