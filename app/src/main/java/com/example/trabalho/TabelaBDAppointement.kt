package com.example.trabalho

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDAppointement ( db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CAMPO_NAME TEXT NOT NULL, " +
                "$CAMPO_DATE TEXT NOT NULL, " +
                "$CAMPO_TIME TEXT NOT NULL, " +
                "$CAMPO_CLIENT_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_CLIENT_ID) REFERENCES ${TabelaBDClient.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT )")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
            queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDClient.NOME} ON ${TabelaBDClient.CAMPO_NOME} = $CAMPO_CLIENT_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME = "appointment"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NAME = "appointment name "
        const val CAMPO_DATE = "date"
        const val CAMPO_TIME = "time"
        const val CAMPO_CLIENT_ID = "clientId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID,
            CAMPO_NAME,
            CAMPO_DATE,
            CAMPO_TIME,
            CAMPO_CLIENT_ID, TabelaBDClient.CAMPO_NOME
        )
    }
}