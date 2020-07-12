package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter2.newsreader.network

import android.util.Log

class FeedObservable private constructor() {

    companion object {
        private val TAG = FeedObservable::class.java.simpleName

        fun getFeed(url: String) = RawNetworkObservable.create(url)
            .map {
                val parser = FeedParser()

                try {
                    val entries = parser.parse(it.body()?.byteStream())
                    Log.v(TAG, "Number of entries from url $url: ${entries.size}")
                    return@map entries
                } catch (e: Exception) {
                    Log.e(TAG, "Error passing feed", e)
                }
                return@map ArrayList<Entry>()
            }
    }
}
