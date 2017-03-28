package com.sample.pronunciation.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.sample.pronunciation.R;
import com.sample.pronunciation.presenter.MainPresenterImpl;

public class MainActivity extends FragmentActivity {

    private MainPresenterImpl mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
