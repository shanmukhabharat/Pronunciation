package com.sample.pronunciation.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sample.pronunciation.R;
import com.sample.pronunciation.Views.MainView;

public class ResultsFragment extends Fragment implements MainView {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public ResultsFragment() {
    }

    public static ResultsFragment newInstance() {
        return new ResultsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        return fragmentView;
    }


//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void stopProgress() {

    }

    @Override
    public void showAllItems() {

    }

    @Override
    public void showResult() {

    }

    @Override
    public void deleteItem() {

    }

    @Override
    public void deleteAllItems() {

    }

    @Override
    public void getAllItems() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
