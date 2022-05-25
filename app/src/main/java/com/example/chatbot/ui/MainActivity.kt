package com.example.chatbot.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.data.Messages
import com.example.chatbot.utils.BotResponse
import com.example.chatbot.utils.Constants.CALL_POLY
import com.example.chatbot.utils.Constants.MAIL_POLY
import com.example.chatbot.utils.Constants.OPEN_GOOGLE
import com.example.chatbot.utils.Constants.OPEN_MAP
import com.example.chatbot.utils.Constants.OPEN_POLY
import com.example.chatbot.utils.Constants.OPEN_SEARCH
import com.example.chatbot.utils.Constants.RECEIVE_ID
import com.example.chatbot.utils.Constants.SEND_ID
import com.example.chatbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MessagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this@MainActivity.supportActionBar!!.setDisplayShowHomeEnabled(true);
        this@MainActivity.supportActionBar!!.setIcon(R.drawable.new_logo)
        this@MainActivity.supportActionBar!!.setBackgroundDrawable( ColorDrawable(Color.parseColor("#ffffff")))
        this@MainActivity.supportActionBar!!.setTitle(Html.fromHtml("<font color='#0065A8'> EPSOT</font>"));

        recyclerView()

        clickEvents()

        val random =(0..3).random()
        customMessage("Hello! Today you are speaking with EPSOT, for more information tape 'help'")
        
    }

    private fun clickEvents(){
        btn_send.setOnClickListener{
            sendMessage()
        }
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }

    }

    private fun recyclerView(){
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager =  LinearLayoutManager(applicationContext)

    }
    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()){
            et_message.setText("")
            adapter.insertMessage(Messages(message,SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)
            botResponse(message)
        }
    }

    private fun botResponse(message:String){
        val timeStamp= Time.timeStamp()
        GlobalScope.launch{
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                adapter.insertMessage(Messages(response,RECEIVE_ID,timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)
                when(response){
                    OPEN_GOOGLE ->{
                        val site=Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH ->{
                        val site=Intent(Intent.ACTION_VIEW)
                        val searchTerm:String? = message.substringAfter("search")
                        site.data= Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    OPEN_POLY ->{
                        val site=Intent(Intent.ACTION_VIEW)
                        site.data= Uri.parse("https://www.polytecsousse.tn/")
                        startActivity(site)
                    }
                    OPEN_MAP -> {
                        val mapIntent = Intent(Intent.ACTION_VIEW)
                        mapIntent.data = Uri.parse("geo:35.826819,10.588957")
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                    }
                    CALL_POLY -> {
                        if(ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.CALL_PHONE),1)


                        }else{
                            val random = (0..3).random()
                            var phone = ""
                            when(random){
                                0 -> phone = "(+216) 99 277 877"
                                1 -> phone = "(+216) 96 277 877"
                                2 -> phone = "(+216) 73 277 877"
                                3 -> phone = "(+216) 92 277 807"
                            }
                            val uri = Uri.parse("tel:$phone")
                            val i = Intent(Intent.ACTION_DIAL,uri)
                            startActivity(i)
                        }

                    }
                    MAIL_POLY -> {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:contact@polytecsousse.tn")
                        }
                        startActivity(Intent.createChooser(emailIntent, "Send Email"))
                    }
                }
            }
        }

    }

    override fun onStart(){
        super.onStart()
        GlobalScope.launch{
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }


    private fun customMessage(message:String){
        GlobalScope.launch{
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp= Time.timeStamp()
                adapter.insertMessage(Messages(message, RECEIVE_ID,timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
}