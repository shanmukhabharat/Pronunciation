package com.sample.pronunciation.Controllers;

import com.sample.pronunciation.Model.OCRModels.OCRResponseModel;
import com.sample.pronunciation.Utils.GlobalConstants;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkController implements Callback<List<OCRResponseModel>> {


    private static ApiEndpoints mNetworkController;

    public static ApiEndpoints getNetworkController() {

        if (mNetworkController == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalConstants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mNetworkController = retrofit.create(ApiEndpoints.class);
            return mNetworkController;
        }

        return mNetworkController;
    }


    public static void getOCRResult(String imageName, String language, boolean isOverlayRequired) {
        getResult(imageName, language, isOverlayRequired);
    }

    private static void getResult(String imageName, String language, boolean isOverlayRequired) {

        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        File file = new File("/storage/emulated/0/Pictures/MyApp/test.png");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        Call<OCRResponseModel> call = mNetworkController.createOCRRequest(GlobalConstants.API_KEY,
                requestBody,
                language,
                isOverlayRequired);
    }

    @Override
    public void onResponse(Call<List<OCRResponseModel>> call, Response<List<OCRResponseModel>> response) {

    }

    @Override
    public void onFailure(Call<List<OCRResponseModel>> call, Throwable t) {

    }
}
