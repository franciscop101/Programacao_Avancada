package com.example.trabalho


import android.content.ContentValues
//import android.provider.BaseColumns


data class Client(var nome: String) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDClient.CAMPO_NOME, nome)

        return valores
    }
}