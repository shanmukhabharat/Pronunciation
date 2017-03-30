package com.sample.pronunciation.presenter;


import com.sample.pronunciation.views.MainView;

public class MainPresenterImpl implements Presenter {

    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }


    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onDestroy() {
        mainView = null;
    }
}
