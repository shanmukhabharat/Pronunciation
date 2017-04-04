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
import com.sample.pronunciation.presenter.MainPresenterImpl;
import com.sample.pronunciation.views.MainViewFunctionalities;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultsFragment extends Fragment implements MainViewFunctionalities.ResultsFragmentFunctions {
	
	private MainPresenterImpl mMainPresenter;
	
	private ResultsFragmentInteraction mListener;
	
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	
	@BindView(R.id.progressBar)
	ProgressBar mProgressBar;
	
	public ResultsFragment() {
		
	}
	
	public interface ResultsFragmentInteraction {
		
		void onFragmentInteraction(Uri uri);
	}
	
	public static ResultsFragment newInstance() {
		
		return new ResultsFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mMainPresenter = MainPresenterImpl.getInstance(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
		
		View fragmentView = inflater.inflate(R.layout.fragment_results_layout, container, false);
		
		ButterKnife.bind(this, fragmentView);
		
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
	public void startProgress() {
		
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void stopProgress() {
		
		mProgressBar.setVisibility(View.GONE);
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
	
	@Override
	public void showFailure(String message) {
		
	}
}
