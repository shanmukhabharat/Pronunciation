package com.sample.pronunciation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.pronunciation.R;
import com.sample.pronunciation.model.OCRModels.OCRResponseModel;

public class DictionaryFragment extends Fragment {

    private static final String ARG_PARAM1 = "OCRResponseModel";

    private OCRResponseModel ocrResponseModel;

    public DictionaryFragment() {
        // Required empty public constructor
    }

    public static DictionaryFragment newInstance(OCRResponseModel responseModel) {
        DictionaryFragment fragment = new DictionaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, responseModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ocrResponseModel = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

}
