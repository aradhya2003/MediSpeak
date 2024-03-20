package com.aradhya.MediSpeak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aradhya.MediSpeak.barcode.BarcodeActivity
import com.aradhya.MediSpeak.text.TextRecognitionActivity
import com.aradhya.MediSpeak.R
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
/*btn barcode is actually for recognition*/
        btnLabeler.setOnClickListener {
            startActivity(Intent(this, BarcodeActivity::class.java))
        }

//        btnLabel.setOnClickListener {
//            startActivity(Intent(this,BarcodeActivity::class.java))
//        }

        btnTextR.setOnClickListener {
            startActivity(Intent(this,TextRecognitionActivity::class.java))
        }

    }

}