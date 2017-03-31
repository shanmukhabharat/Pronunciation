package com.sample.pronunciation.model;

import com.sample.pronunciation.model.OCRModels.OCRResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


interface ApiEndpoints {

    @FormUrlEncoded
    @POST("image")
    Call<OCRResponseModel> createOCRRequest(@Header("apikey") String apikey,
                                            @Field("url") String file,
                                            @Field("language") String lan,
                                            @Field("isOverlayRequired") boolean isOverlayRequired);
}
