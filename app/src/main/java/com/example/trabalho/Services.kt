package com.example.trabalho

import android.content.ContentValues

data class Services(
    var id: Long,
    var service_type : String,
    var duration: String,
    var idAppoitment: Long
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDService.CAMPO_SERVICE_TYPE, service_type)
        valores.put(TabelaBDService.CAMPO_DURATION, duration)
        valores.put(TabelaBDService.CAMPO_APPOINTMENT_ID, idAppoitment)

        return valores
    }
}