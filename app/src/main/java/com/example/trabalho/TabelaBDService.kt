package com.example.trabalho

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDService (db: SQLiteDatabase) : TabelaBD(db, NOME){
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$CAMPO_SERVICE_TYPE TEXT NOT NULL) ")
    }

    companion object{
        const val NOME = "service"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_SERVICE_TYPE = "serviceType"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID,
            CAMPO_SERVICE_TYPE,
            )

    }
}