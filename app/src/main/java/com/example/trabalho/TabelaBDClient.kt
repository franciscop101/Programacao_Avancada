package com.example.trabalho


import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDClient (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
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

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_DATE_BIRTH, CAMPO_PHONE_NUMBER, CAMPO_EMAIL)
    }
}