package com.example.trabalho


import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

//import android.provider.BaseColumns


data class Client(var nome: String = "", var email: String = "", var phone_number: String, var id: Long = -1) :Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDClient.CAMPO_NOME, nome)
        valores.put(TabelaBDClient.CAMPO_PHONE_NUMBER, phone_number)
        valores.put(TabelaBDClient.CAMPO_EMAIL, email)


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Client {

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDClient.CAMPO_NOME)

            val posPhoneNumber = cursor.getColumnIndex(TabelaBDClient.CAMPO_PHONE_NUMBER)
            val posEmail = cursor.getColumnIndex(TabelaBDClient.CAMPO_EMAIL)


            val nome = cursor.getString(posNome)

            val phoneNumber = cursor.getString(posPhoneNumber)
            val email = cursor.getString(posEmail)
            val id = cursor.getLong(posId)

            return Client(nome,email,phoneNumber,id)

        }
    }
}
