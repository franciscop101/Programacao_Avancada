package com.example.trabalho

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


data class Services(


    var service_type : String,
    var duration: String,
    var idAppoitment: Long,

    var id: Long = 1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()


        valores.put(TabelaBDService.CAMPO_SERVICE_TYPE, service_type)
        valores.put(TabelaBDService.CAMPO_DURATION, duration)
        valores.put(TabelaBDService.CAMPO_APPOINTMENT_ID, idAppoitment)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Services {

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posServiceType = cursor.getColumnIndex(TabelaBDService.CAMPO_SERVICE_TYPE)
            val posDuration = cursor.getColumnIndex(TabelaBDService.CAMPO_DURATION)
            val posIdAppoitment = cursor.getColumnIndex(TabelaBDService.CAMPO_APPOINTMENT_ID)


            val serviceType = cursor.getString(posServiceType)
            val duration = cursor.getString(posDuration)
            val idAppoitment = cursor.getLong(posIdAppoitment)
            val id = cursor.getLong(posId)

            return Services(serviceType, duration, idAppoitment, id)
        }
    }
}