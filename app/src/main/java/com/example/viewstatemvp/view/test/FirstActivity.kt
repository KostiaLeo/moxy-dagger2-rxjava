package com.example.viewstatemvp.view.test

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.example.viewstatemvp.R
import kotlinx.android.synthetic.main.activity_first.*

const val REQUEST_CODE = 1

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        name_btn.setOnClickListener {
            startForNameResult()
        }

        login_btn.setOnClickListener {
            startForLoginResult()
        }

        password_btn.setOnClickListener {
            startForPasswordResult()
        }
    }

    private fun startForNameResult() {
        val intent = Intent(this, SecondActivity::class.java)
            .putExtra("type", "name")
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun startForLoginResult() {
        val intent = Intent(this, SecondActivity::class.java)
            .putExtra("type", "login")
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun startForPasswordResult() {
        val intent = Intent(this, SecondActivity::class.java)
            .putExtra("type", "password")
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val response = data?.getStringExtra("response")
                info_tv.text = response
            } else {
                info_tv.text = "Canceled"
            }
        }
    }
}