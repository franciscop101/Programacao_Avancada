package com.example.trabalho

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDService (val db: SQLiteDatabase) {
    fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CAMPO_SERVICE_TYPE TEXT NOT NULL, " +
                "$CAMPO_DURATION TEXT NOT NULL, " +
                "$CAMPO_APPOINTMENT_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_APPOINTMENT_ID) REFERENCES " +
                "${TabelaBDService.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "services"
        const val CAMPO_SERVICE_TYPE = "service_type"
        const val CAMPO_DURATION = "duration"
        const val CAMPO_APPOINTMENT_ID = "appointementId"
    }
}