package com.example.chatbot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatbot.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        this.supportActionBar!!.hide()
        btn_back.setOnClickListener {
            this.finish()
        }
    }
}