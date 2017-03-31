package com.sample.pronunciation.presenter;

import com.sample.pronunciation.model.ApiService;
import com.sample.pronunciation.model.OCRModels.ParsedResult;
import com.sample.pronunciation.views.MainViewFunctionalities;
import java.util.List;

public class MainPresenterImpl implements Presenter, ApiService.ApiResultsListener {

    private MainViewFunctionalities mMainViewFunctionalities;
    private MainViewFunctionalities.ResultsFragmentFunctions mResultsFragmentFunctions;
    private MainViewFunctionalities.ActivityFunctions mActivityFunctions;
    private ApiService.ApiResultsListener mApiResultsListener;
    private static MainPresenterImpl mainPresenter;

    public static MainPresenterImpl getInstance(MainViewFunctionalities mainViewFunctionalities){

        if(mainPresenter == null){
           mainPresenter = new MainPresenterImpl(mainViewFunctionalities);
        }else{
           mainPresenter.mMainViewFunctionalities = mainViewFunctionalities;
        }

        return mainPresenter;
    }

    public static MainPresenterImpl getInstance(MainViewFunctionalities.ActivityFunctions activityFunctions){

        if(mainPresenter == null){
            mainPresenter = new MainPresenterImpl(activityFunctions);
        }else{
            mainPresenter.mActivityFunctions = activityFunctions;
        }

        return mainPresenter;
    }

    public static MainPresenterImpl getInstance(MainViewFunctionalities.ResultsFragmentFunctions resultsFragmentFunctions){

        if(mainPresenter == null){
            mainPresenter = new MainPresenterImpl(resultsFragmentFunctions);
        }else{
            mainPresenter.mResultsFragmentFunctions = resultsFragmentFunctions;
        }

        return mainPresenter;
    }

    private MainPresenterImpl(MainViewFunctionalities mainViewFunctionalities) {
        this.mMainViewFunctionalities = mainViewFunctionalities;
    }

    private MainPresenterImpl(MainViewFunctionalities.ResultsFragmentFunctions resultsFragmentFunctions){
        this.mResultsFragmentFunctions = resultsFragmentFunctions;
    }

    private MainPresenterImpl(MainViewFunctionalities.ActivityFunctions activityFunctions){
        this.mActivityFunctions = activityFunctions;
    }

    @Override
    public void startScan() {
        //Todo  :   Have to handle all the parameters to the api call

        mResultsFragmentFunctions.startProgress();
        ApiService.newInstance(this).doScan("", "", false);
    }


    @Override
    public void showResults(List<ParsedResult> results) {
        mResultsFragmentFunctions.showResult();
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
