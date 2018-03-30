package com.regmoraes.bakingapp.application.di.module;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.regmoraes.bakingapp.application.network.NetworkUtils;
import com.regmoraes.bakingapp.application.network.NoInternetException;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class NetworkModule {

    @Provides
    @Singleton
    public HttpLoggingInterceptor providesLoggingInterceptor() {

        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }

    @Provides
    @Singleton
    public Interceptor providesInternetConnectivityInterceptor(Context context) {

        return chain -> {

            if (!NetworkUtils.hasInternetConnection(context)) {
                throw new NoInternetException();
            }

            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        };
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttp(Interceptor connectivityInterceptor,
                                       HttpLoggingInterceptor loggingInterceptor) {

        return new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient okHttpClient, Gson gson) {

        String RECIPES_API_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

        return new Retrofit.Builder()
                .baseUrl(RECIPES_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }
}
