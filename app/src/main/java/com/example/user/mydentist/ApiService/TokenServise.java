package com.example.user.mydentist.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 19.07.2017.
 */

public interface TokenServise {
    // http://it33.ru/st/index.php?action=checktoken&token=5fb7c61621bca68e966ac4cde8b95ad5
    @GET("index.php?action=checktoken")
    Call<ResponseBody> getId(@Query("token") String token);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://it33.ru/st/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
