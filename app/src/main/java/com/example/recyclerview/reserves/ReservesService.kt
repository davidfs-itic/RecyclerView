package com.example.recyclerview.reserves

import Material
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface ReservesService {

     @GET ("reserves/usuari/{idusuari}")
     suspend fun llistaReserves ( @Path("idusuari") idusuari:Int):List<Reserva>

     @GET("materials/")
     suspend fun llistaMaterials():Response<List<Material>>

     @POST ("reserves/")
     suspend fun afegirReserva(@Body reserva:Reserva): Response<Void>

}

class ReservesAPI{
    companion object  {
        private var mAPI : ReservesService? = null

        //L'anotador per a que sigui thread-safe
        //https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-synchronized/
        @Synchronized
        fun API(): ReservesService {
            if (mAPI == null){
                val client: OkHttpClient = getUnsafeOkHttpClient()
                val gsondateformat= GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsondateformat))
                    .baseUrl("https://3.85.179.9")
                    .client(getUnsafeOkHttpClient())
                    .build()
                    .create(ReservesService::class.java)
            }
            return mAPI!!
        }

    }
}
private fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}