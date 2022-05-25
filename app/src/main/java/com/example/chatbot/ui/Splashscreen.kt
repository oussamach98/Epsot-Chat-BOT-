package com.example.chatbot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatbot.R
import kotlinx.android.synthetic.main.activity_splashscreen.*

class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        this.supportActionBar!!.hide()
        btn_chat.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
        tv_knowMe.setOnClickListener {
            val i = Intent(this,AboutActivity::class.java)
            startActivity(i)
        }
    }
}