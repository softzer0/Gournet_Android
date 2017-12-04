package com.gournet.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gournet.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplitFragment extends Fragment {


    public SplitFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SplitFragment newInstance() {
        SplitFragment fragment = new SplitFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               return inflater.inflate(R.layout.split_pane_layout,container,false);

    }

}
