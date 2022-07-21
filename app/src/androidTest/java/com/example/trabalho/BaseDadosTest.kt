package com.example.trabalho

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

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
        assertNotEquals(-1, client.id)
    }

    private fun insereService(db: SQLiteDatabase, service: Services) {
        service.id = TabelaBDService(db).insert(service.toContentValues())
        assertNotEquals(-1, service.id)
    }

    private fun insereAppointment(db: SQLiteDatabase, appointment: Appointment) {
        appointment.id = TabelaBDAppointement(db).insert(appointment.toContentValues())
        assertNotEquals(-1, appointment.id)
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

        insereService(db, Services("service_teste"))

        db.close()
    }


    @Test
    fun consegueInserirClient() {
        val db = getWritableDatabase()

        insereClient(db, Client("Teste2","teste2@gmail.com","935654478"))

        db.close()
    }

    @Test
    fun consegueInserirAppointment() {
        val db = getWritableDatabase()

        val client = Client("Teste3","teste3@gmail.com","935654378")
        insereClient(db, client)

        val appointment = Appointment("kakak",10,11112022,client)
        insereAppointment(db, appointment)


        db.close()

    }

    @Test
    fun consegueAlterarClient() {
        val db = getWritableDatabase()

        val client = Client("Test4","teste4@gmail.com","935654378")
        insereClient(db, client)

        client.nome = "Joao"
        client.phone_number = "935486426"
        client.email = "joao@gmail.com"

        val  registosAlterados = TabelaBDClient(db).update(
            client.toContentValues(),
            "${TabelaBDClient.CAMPO_ID}=?",
            arrayOf("${client.id}")
        )

        assertEquals( 1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueAlterarAppointment() {
        val db = getWritableDatabase()

        val clientAna = Client("Test9","teste9@gmail.com","935655558")
        insereClient(db, clientAna)

        val clientMaria = Client("Test69","teste6@gmail.com","935654378")
        insereClient(db, clientMaria)

        val appointment = Appointment("test55",32,11112022,clientAna)
        insereAppointment(db, appointment)

        appointment.appointment_name = "unhas55"
        appointment.time = 50
        appointment.date = 11112023
        appointment.client = clientMaria


        val  registosAlterados = TabelaBDAppointement(db).update(
            appointment.toContentValues(),
            "${TabelaBDAppointement.CAMPO_ID}=?",
            arrayOf("${appointment.id}"))

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueAlterarService() {
        val db = getWritableDatabase()

        val services = Services("ServiceTeste2")
        insereService(db, services)

        services.service_type = "unhas"


        val  registosAlterados = TabelaBDService(db).update(
            services.toContentValues(),
            "${TabelaBDService.CAMPO_ID}=?",
            arrayOf("${services.id}")
        )

        assertEquals( 1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueEliminarClient() {
        val db = getWritableDatabase()

        val client = Client("Teste7","14-02-2023","925565578")
        insereClient(db, client)


        val  registosEliminados = TabelaBDClient(db).delete(
            "${TabelaBDClient.CAMPO_ID}=?",
            arrayOf("${client.id}")
        )

        assertEquals( 1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarAppointment() {
        val db = getWritableDatabase()

        val client = Client("Teste8","exeli@gmail.com","935643778")
        insereClient(db, client)

        val appointment = Appointment("mariana",10,22122022, client)
        insereAppointment(db, appointment)


        val  registosEliminados = TabelaBDAppointement(db).delete(
            "${TabelaBDAppointement.CAMPO_ID}=?",
            arrayOf("${appointment.id}")
        )

        assertEquals( 1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarService() {
        val db = getWritableDatabase()


        val services = Services("ServiceTeste3")
        insereService(db, services)


        val  registosEliminados = TabelaBDService(db).delete(
            "${TabelaBDService.CAMPO_ID}=?",
            arrayOf("${services.id}")
        )

        assertEquals( 1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerClient() {
        val db = getWritableDatabase()

        val client = Client("Teste13","qwert@gmail.com","931374778")
        insereClient(db, client)

        val  cursor = TabelaBDClient(db).query(
            TabelaBDClient.TODAS_COLUNAS,
            "${TabelaBDClient.CAMPO_ID}=?",
            arrayOf("${client.id}"),
            null,
            null,
            null

        )

        assertEquals( 1, cursor.count)
        assertTrue(cursor.moveToNext())

        val clientBD = Client.fromCursor(cursor)

        assertEquals(client, clientBD)

        db.close()
    }

    @Test
    fun consegueLerAppointement() {
        val db = getWritableDatabase()

        val client = Client("Teste13","qwert@gmail.com","931374778")
        insereClient(db, client)

        val appointment = Appointment("qwer",65,17112022,client)
        insereAppointment(db, appointment)

        val  cursor = TabelaBDAppointement(db).query(
            TabelaBDAppointement.TODAS_COLUNAS,
            "${TabelaBDAppointement.CAMPO_ID}=?",
            arrayOf("${appointment.id}"),
            null,
            null,
            null

        )

        assertEquals( 1, cursor.count)
        assertTrue(cursor.moveToNext())

        val appointmentBD = Appointment.fromCursor(cursor)

        assertEquals(appointment, appointmentBD)

        db.close()
    }

    @Test
    fun consegueLerService() {
        val db = getWritableDatabase()

        val services = Services("ServiceTeste4")
        insereService(db, services)

        val  cursor = TabelaBDService(db).query(
            TabelaBDService.TODAS_COLUNAS,
            "${TabelaBDService.CAMPO_ID}=?",
            arrayOf("${services.id}"),
            null,
            null,
            null

        )

        assertEquals( 1, cursor.count)
        assertTrue(cursor.moveToNext())

        val servicesBD = Services.fromCursor(cursor)

        assertEquals(services, servicesBD)

        db.close()
    }


}