package com.sample.pronunciation.views;

/**
 * Created by Bharath on 27/03/17, March, 2017.
 */
public interface MainViewFunctionalities {

    public interface ActivityFunctions{

    }

    public interface ResultsFragmentFunctions{

        void startProgress();

        void stopProgress();

        void showAllItems();

        void showResult();

        void deleteItem();

        void deleteAllItems();

        void getAllItems();

        void showFailure(String message);

    }
}
