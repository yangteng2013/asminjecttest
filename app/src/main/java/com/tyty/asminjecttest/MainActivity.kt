package com.tyty.asminjecttest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_a.setOnClickListener {
            Log.d(TAG,"点击了按钮A")
        }
        btn_b.setOnClickListener {
            Log.d(TAG,"点击了按钮B")
        }
    }


}