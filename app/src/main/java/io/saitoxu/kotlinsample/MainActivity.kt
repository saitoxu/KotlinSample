package io.saitoxu.kotlinsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyHttpTask({
            if (it == null) {
                println("connection error")
                return@MyHttpTask
            }
            println(it)
        }).execute()
    }
}
