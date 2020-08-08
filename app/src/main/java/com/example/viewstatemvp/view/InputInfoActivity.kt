package com.example.viewstatemvp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.viewstatemvp.R
import kotlinx.android.synthetic.main.activity_input_info.*

class InputInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_info)

        submit_inputs.setOnClickListener {
            if (password_input.text.isNotEmpty()
                && username_input.text.isNotEmpty()
                && login_input.text.isNotEmpty()
            ) {
                result_tv.text = "Success"
            } else {
                result_tv.text = "Error"
            }
        }
    }
}