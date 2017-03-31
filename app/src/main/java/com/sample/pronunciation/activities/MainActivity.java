package com.sample.pronunciation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.sample.pronunciation.R;
import com.sample.pronunciation.presenter.MainPresenterImpl;
import com.sample.pronunciation.views.MainViewFunctionalities;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements MainViewFunctionalities.ActivityFunctions {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab_search)
    FloatingActionButton fab;

    @Bind(R.id.fragments_parent)
    LinearLayout fragmentsParent;

    private MainPresenterImpl mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainPresenter = MainPresenterImpl.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.fab_search)
    public void startScan(){
        mainPresenter.startScan();
    }
}
