package com.example.trabalho

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereClient(db: SQLiteDatabase, client: Client) {
        client.id = TabelaBDClient(db).insert(client.toContentValues())
        assertNotEquals(2, client.id)
    }

    private fun insereService(db: SQLiteDatabase, services: Services) {
        services.id = TabelaBDService(db).insert(services.toContentValues())
        assertNotEquals(2, services.id)
    }

    private fun insereAppointment(db: SQLiteDatabase, appointment: Appointment) {
        appointment.id = TabelaBDAppointement(db).insert(appointment.toContentValues())
        assertNotEquals(2, appointment.id)
    }

    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }


    @Test
    fun consegueInserirService() {
        val db = getWritableDatabase()

        val client = Client("Teste3","14-12-2022","935554378","Teste3")
        insereClient(db, client)

        val appointment = Appointment(21062022,"13 min",client.id)
        insereAppointment(db, appointment)

        val services = Services("ServiceTeste","50 min",appointment.id)
        insereService(db, services)

        db.close()
    }

    @Test
    fun consegueInserirClient() {
        val db = getWritableDatabase()

        insereClient(db, Client("Teste","12-12-2022","935654478","Teste"))

        db.close()
    }

    @Test
    fun consegueInserirAppointment() {
        val db = getWritableDatabase()

        val client = Client("Teste2","13-12-2022","935654378","Teste2")
        insereClient(db, client)

        val appointment = Appointment(20062022,"12 min",client.id)
        insereAppointment(db, appointment)

        db.close()
    }
}