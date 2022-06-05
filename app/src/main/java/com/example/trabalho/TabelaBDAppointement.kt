package com.example.trabalho

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDAppointement ( db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CAMPO_DATE TEXT NOT NULL, " +
                "$CAMPO_TIME TEXT NOT NULL, " +
                "$CAMPO_CLIENT_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_CLIENT_ID) REFERENCES " +
                "${TabelaBDClient.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "services"
        const val CAMPO_DATE = "date"
        const val CAMPO_TIME = "time"
        const val CAMPO_CLIENT_ID = "clientId"
    }
}