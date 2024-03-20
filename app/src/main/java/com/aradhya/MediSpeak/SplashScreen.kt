package com.aradhya.MediSpeak

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.aradhya.MediSpeak.MainActivity

class SplashScreen:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val time:Long =2000
        Handler().postDelayed(Runnable {
            startActivity(Intent(SplashScreen@this, MainActivity::class.java))
            finish()
        },time)

    }
}