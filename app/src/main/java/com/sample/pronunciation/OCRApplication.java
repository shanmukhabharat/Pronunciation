package com.sample.pronunciation;

import android.app.Application;

import com.sample.pronunciation.model.ApiService;

/**
 * Created by Bharath on 08/03/17, March, 2017.
 */
public class OCRApplication extends Application {

    private static ApiService apiService;

    public static ApiService getApiService() {

        if (apiService == null) {
            apiService = new ApiService();
        }

        return apiService;
    }

}
