package com.arriyam.countersocketandroid

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * The following lines connects the Android app to the server
         * First, the socket is set in setSocket()
         * Than, the connection is established in establishConnection()
         */
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        val countTextView = findViewById<TextView>(R.id.countTextView)
        val resetBtn = findViewById<Button>(R.id.resetBtn)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val successTextView = findViewById<TextView>(R.id.successTextView)

        // TODO : Set the socket to mSocket in getSocket()
        val mSocket = SocketHandler.getSocket()
        println("Socket: $mSocket")

        resetBtn.setOnClickListener {
            resetBtn.visibility = Button.INVISIBLE
            imageView.visibility = ImageView.VISIBLE
            successTextView.visibility = TextView.INVISIBLE
            countTextView.text = ""
        }

        /**
         * This is the listener for the socket
         * set the params inside on() to the event name you want to listen to
         */
        mSocket.on("jeungga") {
            args -> Log.i("MainActivity", "data : ${args[0]}")
            /**
             * BTW it returns an array of objects so you can check in array[0] for the data for sure
             */
            if (args[0] != null) {
                // TODO : I'm parse the data into Model class ResponseModel, so if anything change inside the Json you can check that file !!! (it using package GSON)
                val responseModel = Gson().fromJson(args[0].toString(), ResponseModel::class.java)
                if (responseModel.content != null) {
                        runOnUiThread {
                            countTextView.text = "From: ${responseModel.msgInfo.from} \n to: ${responseModel.msgInfo.to} \n Id: ${responseModel.content.id} \n"
                            successTextView.text = "Success"
                            imageView.visibility = ImageView.INVISIBLE
                            successTextView.visibility = TextView.VISIBLE
                            resetBtn.visibility = Button.VISIBLE
                        }
                }
            }
        }
    }
}
