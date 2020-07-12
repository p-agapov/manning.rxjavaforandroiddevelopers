package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter2.newsreader.network

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RawNetworkObservable private constructor() {

    companion object {
        private val TAG = RawNetworkObservable::class.java.simpleName

        fun create(url: String): Observable<Response> = Observable.create(
            object : ObservableOnSubscribe<Response> {

                val client = OkHttpClient()

                override fun subscribe(emitter: ObservableEmitter<Response>) {
                    try {
                        val response = client.newCall(
                            Request.Builder()
                                .url(url)
                                .build()
                        ).execute()
                        emitter.onNext(response)
                        emitter.onComplete()
                        if (!response.isSuccessful) {
                            emitter.onError(Exception("Error!"))
                        }
                    } catch (e: IOException) {
                        emitter.onError(e)
                    }
                }
            }
        ).subscribeOn(Schedulers.io())

        fun getString(url: String) = create(url)
            .map {
                try {
                    it.body()?.string()
                } catch (e: Exception) {
                    Log.e(TAG, "Error reading url $url", e)
                }
                null
            }
    }
}
