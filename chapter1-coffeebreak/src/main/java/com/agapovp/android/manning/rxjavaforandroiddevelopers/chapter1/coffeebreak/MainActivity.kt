package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter1.coffeebreak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.jakewharton.rxbinding4.widget.textChanges
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switch_button.checkedChanges()
            .subscribe { text_view_1.text = "isChecked: $it" }

        edit_text.textChanges()
            .subscribe { text_view_2.text = if (it.length > 7) "Text too long!" else "" }
    }
}
