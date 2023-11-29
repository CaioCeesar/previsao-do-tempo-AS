package com.example.fragmentosdataehora;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("v1/forecast")
    Call<ResponseBody> getDados(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("hourly") String hourly,
            @Query("forecast_days") String forecast_days);
}
