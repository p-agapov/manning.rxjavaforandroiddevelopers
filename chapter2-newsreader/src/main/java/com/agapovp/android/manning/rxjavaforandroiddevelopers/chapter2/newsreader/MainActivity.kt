package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter2.newsreader

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter2.newsreader.network.Entry
import com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter2.newsreader.network.FeedObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feedUrls = arrayListOf(
            "https://news.google.com/atom?hl=en-US&gl=US&ceid=US:en",
            "https://www.theregister.com/software/headlines.atom",
            "http://www.linux.com/news/soware?format=feed&type=atom"
        )

        val observableList = ArrayList<Observable<List<Entry>>>()

        for (feedUrl: String in feedUrls) {
            val feedObservable = FeedObservable.getFeed(feedUrl)
                .onErrorReturn { ArrayList<Entry>() }

            observableList.add(feedObservable)
        }

        val combinedObservable = Observable
            .combineLatest(observableList) {
                val combinedList = ArrayList<Entry>()
                for (list: Any in it) {
                    combinedList.addAll(list as List<Entry>)
                }
                return@combineLatest combinedList
            }

        combinedObservable
            .map {
                ArrayList<Entry>().apply {
                    this.addAll(it)
                    this.sort()
                }
            }
            .flatMap { Observable.fromIterable<Entry>(it) }
            .take(6)
            .map { it.toString() }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list -> drawList(list) }
    }

    private fun drawList(listItems: List<String>) {
        list.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
