package com.sample.pronunciation.model;

import com.sample.pronunciation.model.OCRModels.OCRResponseModel;
import com.sample.pronunciation.utils.GlobalUtils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService implements Callback<OCRResponseModel>{

    public static final String TAG = ApiService.class.getSimpleName();

    private static ApiService apiService;
    private Retrofit mRetrofitInstance;
    private ApiEndpoints mEndPointsInterface;
    private final ApiResultsListener mApiResultsListener;

    public interface ApiResultsListener{
        void handleResponse(OCRResponseModel response);
        void showFailure(String message);
    }

    public static ApiService newInstance(ApiResultsListener apiResultsListener){

        if(apiService == null){
            apiService = new ApiService(apiResultsListener);
        }

        return apiService;
    }

    private ApiService(ApiResultsListener apiResultsListener){

        if(mRetrofitInstance == null){

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            
            httpClient.addInterceptor(logging);

            mRetrofitInstance = new Retrofit.Builder()
                                    .baseUrl(GlobalUtils.API_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(httpClient.build())
                                    .build();

            mEndPointsInterface =  mRetrofitInstance.create(ApiEndpoints.class);

        }

        this.mApiResultsListener = apiResultsListener;
    }

    public void doScan(String imagePath, String language, boolean isOverlayRequired){

//        MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
//        File file = new File(imagePath);
//        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);
//        MultipartBody.Part filePart = MultipartBody.Part.create(requestBody);

        String base64String = //Base64.encodeToString(FileUtils.readFileToByteArray(file), Base64.DEFAULT);
                "https://www.warm-glass.co.uk/images/products/block-letters-decal-XqUA.jpg";

        Call<OCRResponseModel> call =   mEndPointsInterface.createOCRRequest(GlobalUtils.API_KEY,
                                                                                base64String,
                                                                                "chs",
                                                                                isOverlayRequired);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<OCRResponseModel> call, Response<OCRResponseModel> response) {
        mApiResultsListener.handleResponse(response.body());
    }

    @Override
    public void onFailure(Call<OCRResponseModel> call, Throwable throwable) {
        mApiResultsListener.showFailure(throwable.getMessage());
    }
}
