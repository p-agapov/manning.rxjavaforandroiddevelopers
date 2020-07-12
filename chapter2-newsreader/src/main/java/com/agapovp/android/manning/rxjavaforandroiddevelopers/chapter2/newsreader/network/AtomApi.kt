package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter2.newsreader.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface AtomApi {

    @GET
    fun getFeed(@Url url: String): Call<ResponseBody>
}
