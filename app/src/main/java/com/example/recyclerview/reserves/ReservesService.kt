package com.example.recyclerview.reserves

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
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
}

class ReservesAPI{
    companion object  {
        private var mAPI : ReservesService? = null

        //L'anotador per a que sigui thread-safe
        //https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-synchronized/
        @Synchronized
        fun API(): ReservesService {
            if (mAPI == null){
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://192.168.1.101:8443")
                    .build()
                    .create(ReservesService::class.java)
            }
            return mAPI!!
        }

    }
}
