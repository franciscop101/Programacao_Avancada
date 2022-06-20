package com.example.trabalho

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Appointment(

    var date : Long,
    var time: String,
    var idclient: Long,
    var id: Long = 1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDAppointement.CAMPO_DATE, date)
        valores.put(TabelaBDAppointement.CAMPO_TIME, time)
        valores.put(TabelaBDAppointement.CAMPO_CLIENT_ID, idclient)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Appointment {

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posDate = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_DATE)
            val posTime = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_TIME)
            val posIdClient = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_CLIENT_ID)


            val date = cursor.getLong(posDate)
            val time = cursor.getString(posTime)
            val idClient = cursor.getLong(posIdClient)
            val id = cursor.getLong(posId)

            return Appointment(date, time, idClient, id)
        }
    }
}