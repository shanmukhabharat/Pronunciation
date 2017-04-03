package com.sample.pronunciation.presenter;

import android.util.Log;

import com.sample.pronunciation.model.ApiService;
import com.sample.pronunciation.model.OCRModels.OCRResponseModel;
import com.sample.pronunciation.model.OCRModels.ParsedResult;
import com.sample.pronunciation.utils.ApiServiceException;
import com.sample.pronunciation.utils.GlobalUtils;
import com.sample.pronunciation.views.MainViewFunctionalities;

import java.util.List;

public class MainPresenterImpl implements Presenter,
                                          ApiService.ApiResultsListener {
	
	private static final String TAG = MainPresenterImpl.class.getSimpleName();
	
	private MainViewFunctionalities mMainViewFunctionalities;
	
	private MainViewFunctionalities.ResultsFragmentFunctions mResultsFragmentFunctions;
	
	private MainViewFunctionalities.ActivityFunctions mActivityFunctions;
	
	private ApiService.ApiResultsListener mApiResultsListener;
	
	private static MainPresenterImpl mainPresenter;
	
	public static MainPresenterImpl getInstance(MainViewFunctionalities mainViewFunctionalities) {
		
		if (mainPresenter == null) {
			mainPresenter = new MainPresenterImpl(mainViewFunctionalities);
		} else {
			mainPresenter.mMainViewFunctionalities = mainViewFunctionalities;
		}
		
		return mainPresenter;
	}
	
	public static MainPresenterImpl getInstance(MainViewFunctionalities.ActivityFunctions activityFunctions) {
		
		if (mainPresenter == null) {
			mainPresenter = new MainPresenterImpl(activityFunctions);
		} else {
			mainPresenter.mActivityFunctions = activityFunctions;
		}
		
		return mainPresenter;
	}
	
	public static MainPresenterImpl getInstance(MainViewFunctionalities.ResultsFragmentFunctions resultsFragmentFunctions) {
		
		if (mainPresenter == null) {
			mainPresenter = new MainPresenterImpl(resultsFragmentFunctions);
		} else {
			mainPresenter.mResultsFragmentFunctions = resultsFragmentFunctions;
		}
		
		return mainPresenter;
	}
	
	private MainPresenterImpl(MainViewFunctionalities mainViewFunctionalities) {
		
		this.mMainViewFunctionalities = mainViewFunctionalities;
	}
	
	private MainPresenterImpl(MainViewFunctionalities.ResultsFragmentFunctions resultsFragmentFunctions) {
		
		this.mResultsFragmentFunctions = resultsFragmentFunctions;
	}
	
	private MainPresenterImpl(MainViewFunctionalities.ActivityFunctions activityFunctions) {
		
		this.mActivityFunctions = activityFunctions;
	}
	
	@Override
	public void startScan() {
		//Todo  :   Have to handle all the parameters to the api call
		
		mResultsFragmentFunctions.startProgress();
		
		ApiService.newInstance(this).doScan("", "", true);
	}
	
	
	@Override
	public void handleResponse(OCRResponseModel response) {
		
		Log.d(TAG, "handleResponse() : " + response.toString());
		
		List<ParsedResult> results;
		
		if (response.getIsErroredOnProcessing()) {
			throw new ApiServiceException((String) response.getErrorMessage());
		}
		
		if (response.getOCRExitCode().equals(GlobalUtils.EXIT_CODE_SUCCESS) ||
				    response.getOCRExitCode().equals(GlobalUtils.EXIT_CODE_PARTIAL_SUCCESS)) {
			
			results = response.getParsedResults();
			
		} else {
			throw new ApiServiceException(GlobalUtils.getMessageFromExitCode(
					response.getOCRExitCode()));
		}
		
		
		
	}
	
	@Override
	public void showFailure(Throwable throwable) {
		
		mResultsFragmentFunctions.stopProgress();
		mResultsFragmentFunctions.showFailure();
	}
	
	@Override
	public void onItemClicked(int position) {
		
	}
	
}
