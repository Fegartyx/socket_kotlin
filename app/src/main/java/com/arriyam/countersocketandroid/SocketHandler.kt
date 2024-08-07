package com.arriyam.countersocketandroid

import android.util.Log
import io.socket.client.IO
import io.socket.client.IO.Options
import io.socket.client.Socket
import java.net.URISyntaxException
import java.util.logging.Logger

object SocketHandler {

    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            /**
             * "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
             * "http://localhost:3000/" will not work
             * If you want to use your physical phone you could use the your ip address plus :3000
             * This will allow your Android Emulator and physical device at your home to connect to the server
             */
            /**
             * to set Headers in socket you can use setExtraHeaders() it needs Map inside it for the params
             */
            val options = Options.builder().setExtraHeaders(mapOf("x-license-id" to listOf("6jorh1-G4E48f-j7YFqZ-zU32PN"), "x-user-id" to listOf("d54d1702ad0f8326224b817c796763c9"))).build()

            // TODO : Don't forget to set options inside socket()
            mSocket = IO.socket("https://socket.mri.id", options)
            Log.i("SocketHandler", "Socket set $mSocket")
        } catch (e: URISyntaxException) {
            Log.e("SocketHandler", "Error setting socket")
            throw RuntimeException(e)
        }
    }

    @Synchronized
    fun getSocket(): Socket {
        Log.i("SocketHandler", "Getting socket $mSocket")
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        try {
            mSocket.connect()
            Log.i("SocketHandler", "Connection established")
        } catch (e: Exception) {
            Log.e("SocketHandler", "Error establishing connection")
        }
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
        Log.i("SocketHandler", "Connection closed")
    }
}
