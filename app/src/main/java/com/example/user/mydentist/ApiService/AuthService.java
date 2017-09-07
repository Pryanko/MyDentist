package com.example.user.mydentist.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 13.07.2017.
 */

public interface AuthService {
    //http://it33.ru/st/index.php?action=gettoken&login=mikle&pass=gbnth123
    @GET("index.php?action=gettoken")
    Call<ResponseBody> getToken(@Query("login") String login, @Query("pass") String pass);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://it33.ru/st/")
            //.addConverterFactory(GsonConverterFactory.create())
            .build();

}
