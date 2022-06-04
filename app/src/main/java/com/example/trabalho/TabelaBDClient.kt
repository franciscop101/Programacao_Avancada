package com.example.trabalho


import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDClient (val db: SQLiteDatabase) {
    fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CAMPO_NOME TEXT NOT NULL,"+
                " $CAMPO_DATE_BIRTH TEXT NOT NULL,"+
                " $CAMPO_PHONE_NUMBER TEXT NOT NULL,"+
                " $CAMPO_EMAIL TEXT NOT NULL)")
    }

    companion object {
        const val NOME = "client"
        const val CAMPO_NOME = "nome"
        const val CAMPO_DATE_BIRTH = "date_birth"
        const val CAMPO_PHONE_NUMBER ="phone_number"
        const val CAMPO_EMAIL = "email"
    }
}