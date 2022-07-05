package com.example.trabalho

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable


data class Services(


    var service_type : String,
    var appoitment: Appointment,

    var id: Long = 1
): Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()


        valores.put(TabelaBDService.CAMPO_SERVICE_TYPE, service_type)
        valores.put(TabelaBDService.CAMPO_APPOINTMENT_ID, appoitment.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Services {

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posServiceType = cursor.getColumnIndex(TabelaBDService.CAMPO_SERVICE_TYPE)
            val posIdAppoitment = cursor.getColumnIndex(TabelaBDService.CAMPO_APPOINTMENT_ID)
            val posNomeAppoint =  cursor.getColumnIndex(TabelaBDAppointement.CAMPO_NAME)
            val posTime =  cursor.getColumnIndex(TabelaBDAppointement.CAMPO_TIME)
            val posClient =  cursor.getColumnIndex(TabelaBDAppointement.CAMPO_CLIENT_ID)
            val posDate =  cursor.getColumnIndex(TabelaBDAppointement.CAMPO_DATE)


            val time = cursor.getString(posTime)
            val client = cursor.getLong(posClient)
            val date = cursor.getLong(posDate)
            val serviceType = cursor.getString(posServiceType)
            val idAppoitment = cursor.getLong(posIdAppoitment)
            val id = cursor.getLong(posId)
            val nomeAppointment = cursor.getString(posNomeAppoint)
            val appoitment = Appointment(nomeAppointment,time,client,date,idAppoitment)

            return Services(serviceType,appoitment,id)

        }
    }
}