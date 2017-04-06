package com.sample.pronunciation.presenter;

import com.sample.pronunciation.model.OCRModels.OCRResponseModel;

/**
 * Created by Bharath on 27/03/17, March, 2017.
 */
interface Presenter {

    void onItemClicked(int position, OCRResponseModel resultItem);

    void startScan();
}
