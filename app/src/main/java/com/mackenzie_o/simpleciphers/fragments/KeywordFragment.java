package com.mackenzie_o.simpleciphers.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mackenzie_o.simpleciphers.MainActivity;
import com.mackenzie_o.simpleciphers.R;

/**
 * Created by MacKenzie_2 on 2/09/2015.
 */
public class KeywordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.keyword_fragment, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(4);

    }
}

