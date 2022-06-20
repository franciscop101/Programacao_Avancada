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

    @Test
    fun consegueAlterarClient() {
        val db = getWritableDatabase()

        val client = Client("Teste4","15-12-2022","935354378","Teste4")
        insereClient(db, client)

        client.nome = "Joao"
        client.date_birthday = "30-01-2001"
        client.phone_number = "935486426"
        client.email = "joao@gmail.com"

        val  registosAlterados = TabelaBDClient(db).update(
            client.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${client.id}")
        )

        assertNotEquals( 2, registosAlterados)

        db.close()

    }

    @Test
    fun consegueAlterarAppointment() {
        val db = getWritableDatabase()

        val client = Client("Teste5","16-12-2022","935674778","Teste5")
        insereClient(db, client)

        val appointment = Appointment(20072022,"32 min",client.id)
        insereAppointment(db, appointment)

        appointment.date = 11012023
        appointment.time = "50 min"


        val  registosAlterados = TabelaBDAppointement(db).update(
            appointment.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${appointment.id}")
        )

        assertNotEquals( 2, registosAlterados)

        db.close()

    }

    @Test
    fun consegueAlterarService() {
        val db = getWritableDatabase()

        val client = Client("Teste6","14-11-2022","935555578","Teste6")
        insereClient(db, client)

        val appointment = Appointment(27062022,"32 min",client.id)
        insereAppointment(db, appointment)

        val services = Services("ServiceTeste2","55 min",appointment.id)
        insereService(db, services)

        services.service_type = "unhas"
        services.duration = "30 min"


        val  registosAlterados = TabelaBDService(db).update(
            services.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${services.id}")
        )

        assertNotEquals( 2, registosAlterados)

        db.close()

    }

    @Test
    fun consegueEliminarClient() {
        val db = getWritableDatabase()

        val client = Client("Teste7","14-02-2023","925565578","Teste7")
        insereClient(db, client)


        val  registosEliminados = TabelaBDClient(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${client.id}")
        )

        assertNotEquals( 2, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarAppointment() {
        val db = getWritableDatabase()

        val client = Client("Teste8","16-11-2022","935643778","Teste8")
        insereClient(db, client)

        val appointment = Appointment(20072022,"35 min",client.id)
        insereAppointment(db, appointment)


        val  registosEliminados = TabelaBDAppointement(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${appointment.id}")
        )

        assertNotEquals( 2, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarService() {
        val db = getWritableDatabase()


        val client = Client("Teste9","13-01-2022","937855578","Teste9")
        insereClient(db, client)

        val appointment = Appointment(13072022,"40 min",client.id)
        insereAppointment(db, appointment)

        val services = Services("ServiceTeste3","56 min",appointment.id)
        insereService(db, services)


        val  registosEliminados = TabelaBDService(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${services.id}")
        )

        assertNotEquals( 2, registosEliminados)

        db.close()
    }

}