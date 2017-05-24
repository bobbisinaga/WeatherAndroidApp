package com.bobbi.weatherandroidapp.data.source.remote;

import android.content.Context;

import com.bobbi.weatherandroidapp.BuildConfig;
import com.bobbi.weatherandroidapp.data.Weather;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

public interface APIService {
    String ENDPOINT = "http://api.openweathermap.org/";
    String API_KEY = "5e64243c49fb13c5dc093984fb78654e";

    @GET("data/2.5/weather?APPID=" + API_KEY)
    Observable<Weather> getWeatherByCityName(@Query("q") String cityName, @Query("units") String units);

    class Factory {

        public static APIService create(Context context) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(60, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                builder.addInterceptor(interceptor);
            }

            //Extra Headers
//            builder.addNetworkInterceptor(chain -> {
//                Request request = chain.request().newBuilder().addHeader("Authorization", authToken).build();
//                return chain.proceed(request);
//            });

            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);
            builder.cache(cache);

            builder.addInterceptor(new UnauthorisedInterceptor(context));
            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(APIService.class);
        }
    }
}
