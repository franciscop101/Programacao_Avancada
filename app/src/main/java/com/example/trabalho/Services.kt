package com.example.trabalho

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable


data class Services(

    var service_type : String,


): Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()


        valores.put(TabelaBDService.CAMPO_SERVICE_TYPE, service_type)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Services {

            val posServiceType = cursor.getColumnIndex(TabelaBDService.CAMPO_SERVICE_TYPE)

            val serviceType = cursor.getString(posServiceType)

            return Services(serviceType)

        }
    }
}