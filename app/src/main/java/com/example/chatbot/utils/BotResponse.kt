package com.example.chatbot.utils

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.chatbot.ui.MainActivity
import com.example.chatbot.utils.Constants.CALL_POLY
import com.example.chatbot.utils.Constants.MAIL_POLY
import com.example.chatbot.utils.Constants.OPEN_GOOGLE
import com.example.chatbot.utils.Constants.OPEN_MAP
import com.example.chatbot.utils.Constants.OPEN_POLY
import com.example.chatbot.utils.Constants.OPEN_SEARCH

object BotResponse {
    fun basicResponses(_message:String):String{
        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when {
            message.contains("hello") ->{
                when(random){
                    0 -> "Hello there!"
                    1 -> "Heeey"
                    2 -> "Sup!"
                    else -> "error"
                }
            }
            message.contains("how are you") ->{
                when(random){
                    0 -> "Doing good! thanks for asking :D"
                    1 -> "I'm alright"
                    2 -> "Doing fine, how about you?"
                    else -> "error"
                }
            }
            message.contains("flip") && message.contains("coin")->{
                var r=(0..1).random()
                val result=if(r==0) "head" else "tails"
                "I flipped a coin and it landed on $result"
            }
            message.contains("solve")->{
                val equation: String? = message.substringAfter("solve")
                return try {
                    val answer = SolveMath.solveMath( equation ?:"0")
                    answer.toString()
                }catch(e:Exception){
                    "Sorry, I can't solve that!"
                }
            }
            message.contains("time") && message.contains("?") -> {
                Time.timeStamp()
            }
            message.contains("open") && message.contains("google") ->{
                OPEN_GOOGLE
            }
            message.contains("website") ->{
                OPEN_POLY

            }
            message.contains("help") ->{
                "List of commands :\n" +
                        "\t- website : open our website \n" +
                        "\t- location : view our location in google maps\n" +
                        "\t- call : call us\n" +
                        "\t- send email : send us an email\n" +
                        "\t- contact : view our contact"

            }

            message.contains("search") ->{
                OPEN_SEARCH
            }
            message.contains("location") ->{
                OPEN_MAP
            }
            message.contains("send") && message.contains("email") ->{
                MAIL_POLY
            }
            message.contains("call") ->{
                CALL_POLY
            }
            message.contains("contact") ->{

                "Email : contact@polytecsousse.tn\n" + "Phone : \n" +
                        "\t(+216) 99 277 877\n" +
                        "\t(+216) 96 277 877\n" +
                        "\t(+216) 73 277 877\n" +
                        "\t(+216) 92 277 807\n"

            }
            else -> {
                when(random){
                    0 -> "I don't understand that"
                    1 -> "Still didn't learn how to responde to that sorry!"
                    2 -> "Try asking something i can answer please"
                    else -> "error"
                }
            }
        }
    }


}