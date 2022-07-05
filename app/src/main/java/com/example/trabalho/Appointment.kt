package com.example.trabalho

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Appointment(

    var appointment_name: String,
    var time: String,
    var date: Long,
    var client: Client,
    var id: Long = 1

) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDAppointement.CAMPO_NAME, appointment_name)
        valores.put(TabelaBDAppointement.CAMPO_DATE, date)
        valores.put(TabelaBDAppointement.CAMPO_TIME, time)
        valores.put(TabelaBDAppointement.CAMPO_CLIENT_ID, client.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Appointment {

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posName = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_NAME)
            val posDate = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_DATE)
            val posTime = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_TIME)
            val posIdClient = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_CLIENT_ID)
            val posNomeClient = cursor.getColumnIndex(TabelaBDClient.CAMPO_NOME)


            val date = cursor.getLong(posDate)
            val time = cursor.getString(posTime)
            val name = cursor.getString(posName)
            val idClient = cursor.getString(posIdClient)
            val id = cursor.getLong(posId)

            val nomeClient = cursor.getString(posNomeClient)
            val client = Client(nomeClient, idClient)


            return Appointment(name,time,date,client,id)

        }
    }
}