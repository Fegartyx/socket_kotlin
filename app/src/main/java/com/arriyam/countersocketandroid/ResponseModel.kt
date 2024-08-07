package com.arriyam.countersocketandroid

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("msg_info") val msgInfo: MsgInfo,
    val content: Content
)

data class MsgInfo(
    val from: String,
    val to: String,
    @SerializedName("msg_time") val msgTime: Long
)

data class Content(
    val id: String,
)

data class Appointment(
    val id: Int,
    @SerializedName("appointment_at") val appointmentAt: String,
    val customer: Customer,
    val treatment: Treatment
)

data class Customer(
    val id: Int,
    val name: String,
    val gender: String
)

data class Treatment(
    val id: Int,
    val name: String
)
