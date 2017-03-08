package com.sample.pronunciation.Controllers;

import com.sample.pronunciation.Model.OCRModels.OCRResponseModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiEndpoints {

    @Multipart
    @POST("image")
    Call<OCRResponseModel> createOCRRequest(@Header("apikey") String apikey,
                                            @Part("file") RequestBody imageFile,
                                            @Field("language") String language,
                                            @Field("isOverlayRequired") boolean isOverlayRequired);
}
