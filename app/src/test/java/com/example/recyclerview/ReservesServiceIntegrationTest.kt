import com.example.recyclerview.reserves.Reserva
import com.example.recyclerview.reserves.ReservesService
import com.example.recyclerview.reserves.toDate
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import org.junit.Assert.assertEquals

class ReservesServiceIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var reservesService: ReservesService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Convertir JSON String a Date
            .create()

        reservesService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ReservesService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test llistaReserves retorna reserves correctament`() = runTest {
        val jsonResponse = """
            [
                {
                    "id": 1,
                    "idmaterial": 2,
                    "idusuari": 5,
                    "datareserva": "2024-02-10T10:00:00",
                    "datafinal": "2024-02-11T12:00:00",
                    "descripcio": "Reserva de projector",
                    "imatge": "https://example.com/projector.jpg"
                },
                {
                    "id": 2,
                    "idmaterial": 3,
                    "idusuari": 5,
                    "datareserva": "2024-02-12T09:30:00",
                    "datafinal": "2024-02-13T14:00:00",
                    "descripcio": "Reserva de portàtil",
                    "imatge": "https://example.com/portatil.jpg"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(jsonResponse)
                .setResponseCode(200)
        )

        val response = reservesService.llistaReserves(5)

        assertEquals(2, response.size)
        assertEquals(1, response[0].idreserva)
        assertEquals(2, response[0].idmaterial)
        assertEquals("Reserva de projector", response[0].descripcio)
    }
    @Test
    fun `test llistaMaterials retorna materials correctament`() = runTest {
        val jsonResponse = """
        [
            {
                "id": 2,
                "descripcio": "Projector HD",
                "imatge": "https://example.com/projector.jpg"
            },
            {
                "id": 3,
                "descripcio": "Portàtil Dell",
                "imatge": "https://example.com/portatil.jpg"
            }
        ]
    """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(jsonResponse)
                .setResponseCode(200)
        )

        val response = reservesService.llistaMaterials()

        assertEquals(true, response.isSuccessful)
        assertEquals(2, response.body()?.size)
        assertEquals("Projector HD", response.body()?.get(0)?.descripcio)
    }

    @Test
    fun `test afegirReserva retorna resposta correcta`() = runTest {
        val reserva = Reserva(
            idreserva = 3,
            idmaterial = 2,
            idusuari = 5,
            datareserva =  ("2024-02-15T09:00:00").toDate("yyyy-MM-dd'T'HH:mm:ss"),
            datafinal = ("2024-02-16T17:00:00").toDate("yyyy-MM-dd'T'HH:mm:ss"),
            descripcio = "Reserva de pantalla interactiva",
            imatge = "https://example.com/pantalla.jpg"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201) // HTTP 201 Created
        )

        val response = reservesService.afegirReserva(reserva)

        assertEquals(201, response.code()) // Comprovar que la resposta és HTTP 201
        assertEquals(true, response.isSuccessful) // Comprovar que la resposta és exitosa
    }
    @Test
    fun `test afegirReserva retorna 400 Bad Request quan les dades són incorrectes`() = runTest {
        val reserva = Reserva(
            idreserva = 3,
            idmaterial = 2,
            idusuari = 5,
            datareserva =  ("2024-02-15T09:00:00").toDate("yyyy-MM-dd'T'HH:mm:ss"),
            datafinal = ("2024-02-16T17:00:00").toDate("yyyy-MM-dd'T'HH:mm:ss"),
            descripcio = "Reserva incorrecta",
            imatge = null // Simulant un camp nulo o mal format
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400) // HTTP 400 Bad Request
                .setBody("{ \"error\": \"Bad request: Missing image\" }")
        )

        val response = reservesService.afegirReserva(reserva)

        assertEquals(400, response.code()) // Comprovar que la resposta és HTTP 400
        assertEquals(false, response.isSuccessful) // La resposta ha de fallar
    }
    @Test
    fun `test llistaMaterials retorna 503 Service Unavailable quan el servidor està inactiu`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(503) // HTTP 503 Service Unavailable
                .setBody("{ \"error\": \"Service unavailable\" }")
        )

        val response = reservesService.llistaMaterials()

        assertEquals(503, response.code()) // Comprovar que la resposta és HTTP 503
        assertEquals(false, response.isSuccessful) // La resposta ha de fallar
    }

}
