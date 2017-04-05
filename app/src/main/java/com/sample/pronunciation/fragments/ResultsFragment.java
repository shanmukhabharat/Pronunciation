package com.sample.pronunciation.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sample.pronunciation.R;
import com.sample.pronunciation.adapters.ScanResultsAdapter;
import com.sample.pronunciation.model.OCRModels.OCRResponseModel;
import com.sample.pronunciation.model.OCRModels.ParsedResult;
import com.sample.pronunciation.presenter.MainPresenterImpl;
import com.sample.pronunciation.views.MainViewFunctionalities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;


public class ResultsFragment extends Fragment implements MainViewFunctionalities.ResultsFragmentFunctions,
                                                         ScanResultsAdapter.OnItemClickListener {

    private final String TAG = ResultsFragment.class.getSimpleName();
	
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	
	@BindView(R.id.progressBar)
	ProgressBar mProgressBar;
	
	private MainPresenterImpl mMainPresenter;
	
	private ResultsFragmentInteraction mListener;
	
	private ScanResultsAdapter mScanResultsAdapter;
	
	private Unbinder unbinder;
	
	public ResultsFragment() {
		
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
		
		unbinder = ButterKnife.bind(this, fragmentView);

		mScanResultsAdapter = new ScanResultsAdapter(this);

		mRecyclerView.setAdapter(mScanResultsAdapter);

		return fragmentView;
	}

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
	public void addResultAtStart(OCRResponseModel model) {
		mScanResultsAdapter.addResultAtStart(model);
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
	
	@Override
	public void showDictionaryEntry() {
		
	}

	@Override
	public void setAdapter(ScanResultsAdapter adapter) {
		mScanResultsAdapter = adapter;
	}

	@Override
	public void onItemClicked(int position, OCRResponseModel resultItem) {
		mMainPresenter.onItemClicked(position, resultItem);
	}

	public interface ResultsFragmentInteraction {
		
		void onFragmentInteraction(Uri uri);
	}
}
