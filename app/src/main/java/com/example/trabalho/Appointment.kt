package com.example.trabalho

data class Appointment(
    var id: Long,
    var date : String,
    var time: String,
    var idclient: Long
) {
}