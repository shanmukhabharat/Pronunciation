package com.sample.pronunciation.presenter;

/**
 * Created by Bharath on 27/03/17, March, 2017.
 */
public interface Presenter {

    void onResume();

    void onItemClicked(int position);

    void onDestroy();
}
