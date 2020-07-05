package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter1.livesearch

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)

        search_results.adapter = arrayAdapter

        edit_text.textChanges()
            .doOnNext { this.clearSearchResults() }
            .filter { it.length >= 3 }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateSearchResults)
    }

    private fun clearSearchResults() {
        arrayAdapter.clear()
    }

    private fun updateSearchResults(text: CharSequence) {
        val list = ArrayList<String>()
        for (i in 0..4) {
            list.add("" + text + Math.random())
        }

        arrayAdapter.clear()
        arrayAdapter.addAll(list)
    }
}
