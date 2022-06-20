package com.example.trabalho

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDService (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$CAMPO_SERVICE_TYPE TEXT NOT NULL, " +
                "$CAMPO_DURATION TEXT NOT NULL, " +
                "$CAMPO_APPOINTMENT_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_APPOINTMENT_ID) REFERENCES ${TabelaBDAppointement.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT )")
    }

    companion object{
        const val NOME = "service"
        const val CAMPO_SERVICE_TYPE = "serviceType"
        const val CAMPO_DURATION = "duration"
        const val CAMPO_APPOINTMENT_ID = "appointmentId"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID,
            CAMPO_SERVICE_TYPE,
            CAMPO_DURATION,
            CAMPO_APPOINTMENT_ID
        )

    }
}