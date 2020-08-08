package com.example.viewstatemvp.view.test

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.example.viewstatemvp.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val description = intent?.getStringExtra("type") ?: ""
        input_data.hint = "input $description"

        submit_data.setOnClickListener {
            val input = input_data.text.toString().takeIf(String::isNotEmpty) ?: run {
                error_tv.visibility = View.VISIBLE
                return@setOnClickListener
            }
            error_tv.visibility = View.GONE
            val intent = Intent().putExtra("response", "received $description: $input")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}