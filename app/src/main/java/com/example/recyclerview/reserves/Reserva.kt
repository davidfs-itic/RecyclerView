package com.example.recyclerview.reserves

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.util.Date

@Parcelize
data class Reserva(
    @SerializedName("id")
    val idreserva: Int,
    val idmaterial: Int,
    val idusuari: Int,
    val datareserva: Date?,
    val datafinal: Date?,
    val descripcio: String,
    val imatge: String?
):Parcelable

class ReservesProvider{
    companion object {
        val Reserves:List<Reserva> = listOf(
            Reserva(1,2, idusuari = 2,"2023-01-01".toDate("yyyy-MM-DD"),"2023-01-15".toDate("yyyy-MM-DD"),"Reserva 1",null),
            Reserva(2,3, idusuari = 2,"2023-02-01".toDate("yyyy-MM-DD"),"2023-01-15".toDate("yyyy-MM-DD"),"Reserva 2",null),
            Reserva(3,4, idusuari = 2,"2023-03-01".toDate("yyyy-MM-DD"),null,"Reserva 3",null),
            Reserva(4,5, idusuari = 2,"2023-04-01".toDate("yyyy-MM-DD"),null,"Reserva 4",null),
        )
    }
}
