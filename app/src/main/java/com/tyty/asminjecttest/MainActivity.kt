package com.tyty.asminjecttest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tyty.asminjecttest.hook.HookOnClicklistener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_a.setOnClickListener {
//            Log.d(TAG,"点击了按钮A")
            Toast.makeText(this@MainActivity, "点击了按钮A。。", Toast.LENGTH_SHORT).show();
        }
        btn_b.setOnClickListener {
            Log.d(TAG,"点击了按钮B")
        }

        HookOnClicklistener.hook(this,btn_a);
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}