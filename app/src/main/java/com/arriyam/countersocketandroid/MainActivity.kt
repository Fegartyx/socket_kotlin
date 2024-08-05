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

        // The following lines connects the Android app to the server
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

//        val counterBtn = findViewById<Button>(R.id.counterBtn)
        val countTextView = findViewById<TextView>(R.id.countTextView)
//        val disconnectBtn = findViewById<Button>(R.id.disconnectBtn)
        val resetBtn = findViewById<Button>(R.id.resetBtn)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val successTextView = findViewById<TextView>(R.id.successTextView)

        var mSocket = SocketHandler.getSocket()
        println("Socket: $mSocket")

//        counterBtn.setOnClickListener{
//            Log.i("MainActivity", "Button clicked")
//            print("Button clicked")
//            mSocket.emit("jeungga", "Clicked")
//        }
//
//        disconnectBtn.setOnClickListener {
//            Log.i("MainActivity", "Disconnect button clicked")
//            SocketHandler.closeConnection()
//        }

        resetBtn.setOnClickListener {
            resetBtn.visibility = Button.INVISIBLE
            imageView.visibility = ImageView.VISIBLE
            successTextView.visibility = TextView.INVISIBLE
            countTextView.text = ""
        }

        mSocket.on("jeungga") {
            args -> Log.i("MainActivity", "data : ${args[0]}")
            if (args[0] != null) {
                val responseModel = Gson().fromJson(args[0].toString(), ResponseModel::class.java)
                if (responseModel.content.appointments.isNotEmpty()) {
                    for (appointment in responseModel.content.appointments) {
                        val customer = appointment.customer
                        val treatment = appointment.treatment
                        runOnUiThread {
                            countTextView.text = "Name: ${responseModel.content.name} \n Appoint At : ${appointment.appointmentAt} \n Customer: ${customer.name} \n Treatment: ${treatment.name}"
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
}
