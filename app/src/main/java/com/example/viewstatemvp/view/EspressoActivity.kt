package com.example.viewstatemvp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewstatemvp.R
import kotlinx.android.synthetic.main.activity_espresso.*

class EspressoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espresso)

        var i = 0

        click_test_btn.setOnClickListener {
            if (i == 0) {
                tv_display.text = getString(R.string.travis)
                i = 1
            } else {
                tv_display.text = getString(R.string.for_real)
                i = 0
            }
        }

        submit.setOnClickListener {
            val text = input_text.text.toString()
            output_text.text = if (text.isNotEmpty()) "Output:$text" else "type smth"
        }
    }
}