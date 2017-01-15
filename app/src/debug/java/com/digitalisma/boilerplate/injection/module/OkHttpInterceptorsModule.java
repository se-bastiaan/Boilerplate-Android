package com.digitalisma.boilerplate.injection.module;

import android.support.annotation.NonNull;

import com.digitalisma.boilerplate.data.local.PreferencesHelper;
import com.digitalisma.boilerplate.data.remote.DynamicUrlInterceptor;
import com.digitalisma.boilerplate.injection.OkHttpInterceptors;
import com.digitalisma.boilerplate.injection.OkHttpNetworkInterceptors;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import static java.util.Collections.singletonList;

@Module
public class OkHttpInterceptorsModule {

    // Provided as separate dependency for Developer Settings to be able to change HTTP log level at runtime.
    @Provides
    @Singleton
    @NonNull
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.d(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    @NonNull
    public DynamicUrlInterceptor provideDynamicBaseUrlInterceptor(final PreferencesHelper preferencesHelper) {
        return new DynamicUrlInterceptor(preferencesHelper);
    }

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpInterceptors(DynamicUrlInterceptor urlInterceptor, @NonNull
        HttpLoggingInterceptor httpLoggingInterceptor) {
        return Arrays.asList(urlInterceptor, httpLoggingInterceptor);
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return new ArrayList<>(singletonList(new StethoInterceptor()));
    }

}