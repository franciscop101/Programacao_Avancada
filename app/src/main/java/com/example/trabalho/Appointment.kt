package com.example.trabalho

import android.content.ContentValues

data class Appointment(
    var id: Long,
    var date : String,
    var time: String,
    var idclient: Long
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDAppointement.CAMPO_DATE, date)
        valores.put(TabelaBDAppointement.CAMPO_TIME, time)
        valores.put(TabelaBDAppointement.CAMPO_CLIENT_ID, idclient)

        return valores
    }
}