package com.hcaula.smashpe.challonge

import com.hcaula.smashpe.BuildConfig
import okhttp3.OkHttpClient

class HttpClient() {
    var client: OkHttpClient
    private val apiKey = BuildConfig.CHALLONGE_SECRET

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
        client = httpClient.build()
    }
}
