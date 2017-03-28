package com.sample.pronunciation.model;

import android.util.Log;

import com.sample.pronunciation.Utils.GlobalConstants;
import com.sample.pronunciation.model.OCRModels.OCRResponseModel;
import com.sample.pronunciation.model.OCRModels.ParsedResult;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    public static final String TAG = ApiService.class.getSimpleName();

    private Retrofit mRetrofitInstance;
    private ApiEndpoints mEndPointsInterface;

    public ApiService(){

        if(mRetrofitInstance == null){

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);

            mRetrofitInstance = new Retrofit.Builder()
                                    .baseUrl(GlobalConstants.API_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(httpClient.build())
                                    .build();

            mEndPointsInterface =  mRetrofitInstance.create(ApiEndpoints.class);

        }

    }

    public void getOCRResult(String imagePath, String language, boolean isOverlayRequired) throws IOException{

//        MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
//        File file = new File(imagePath);
//        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);
//        MultipartBody.Part filePart = MultipartBody.Part.create(requestBody);

        String base64String = //Base64.encodeToString(FileUtils.readFileToByteArray(file), Base64.DEFAULT);
                "https://www.warm-glass.co.uk/images/products/block-letters-decal-XqUA.jpg";

        Call<OCRResponseModel> call =   mEndPointsInterface.createOCRRequest(
                                                GlobalConstants.API_KEY,
                                                base64String, "eng",
                                                isOverlayRequired);

        call.enqueue(new Callback<OCRResponseModel>() {
            @Override
            public void onResponse(Call<OCRResponseModel> call, Response<OCRResponseModel> response) {
                List<ParsedResult> results = response.body().getParsedResults();
                Log.d(TAG, "response : "+response.toString());
            }

            @Override
            public void onFailure(Call<OCRResponseModel> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

}
