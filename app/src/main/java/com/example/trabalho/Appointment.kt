package com.example.trabalho

data class Appointment(
    var id: Long,
    var service_type : String,
    var duration: String,
    var idclient: Long
) {
}