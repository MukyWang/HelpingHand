package com.muk.helpinghand.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muk.helpinghand.R;

/**
 * Created by Zelta on 7/05/16.
 */
public class MainFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.main_fragment, container, false);
        return myView;
    }
}
