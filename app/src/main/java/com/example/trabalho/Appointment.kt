package com.example.trabalho

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Appointment(

    var appointment_name: String,
    var time: Long,
    var date: Long,
    var client: Client,
    var id: Long = -1

) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDAppointement.CAMPO_NAME_APPOINTMENT, appointment_name)
        valores.put(TabelaBDAppointement.CAMPO_DATE, date)
        valores.put(TabelaBDAppointement.CAMPO_TIME, time)
        valores.put(TabelaBDAppointement.CAMPO_CLIENT_ID, client.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Appointment {

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posDate = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_DATE)
            val posTime = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_TIME)
            val posNomeAppointment = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_NAME_APPOINTMENT)
            val posIdClient = cursor.getColumnIndex(TabelaBDAppointement.CAMPO_CLIENT_ID)
            val posPoneNumber = cursor.getColumnIndex(TabelaBDClient.CAMPO_PHONE_NUMBER)
            val posEmail = cursor.getColumnIndex(TabelaBDClient.CAMPO_EMAIL)
            val posNomeClient = cursor.getColumnIndex(TabelaBDClient.CAMPO_NOME)

            val id = cursor.getLong(posId)
            val date = cursor.getLong(posDate)
            val time = cursor.getLong(posTime)
            val name = cursor.getString(posNomeAppointment)

            val idClient = cursor.getLong(posIdClient)
            val phone_number = cursor.getString(posPoneNumber)
            val email = cursor.getString(posEmail)
            val nomeClient = cursor.getString(posNomeClient)
            val client = Client(nomeClient,email,phone_number,idClient)

            return Appointment(name,time,date,client,id)

        }
    }
}