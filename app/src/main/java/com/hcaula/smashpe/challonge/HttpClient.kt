package com.hcaula.smashpe.challonge

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class HttpClient(context: Context) {
    var client: OkHttpClient
    private val apiKey = context
        .getSharedPreferences("Settings", Context.MODE_PRIVATE)
        .getString("API_KEY", "")

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            val url = original
                .url()
                .newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }

        httpClient
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
            )

        client = httpClient.build()
    }
}
