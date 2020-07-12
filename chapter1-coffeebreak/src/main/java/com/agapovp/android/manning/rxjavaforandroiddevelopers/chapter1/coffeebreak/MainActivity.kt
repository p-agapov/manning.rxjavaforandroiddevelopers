package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter1.coffeebreak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switch_button.checkedChanges().skipInitialValue()
            .subscribe { text_view_1.text = "isChecked: $it" }

        edit_text_1.textChanges()
            .subscribe { text_view_2.text = if (it.length > 7) "Text too long!" else "" }

        Observable.combineLatest(
            edit_text_1.textChanges(), edit_text_2.textChanges(),
            BiFunction { t1: CharSequence, t2: CharSequence -> "$t1 $t2" })
            .subscribe { text_view_3.text = it }
    }
}
