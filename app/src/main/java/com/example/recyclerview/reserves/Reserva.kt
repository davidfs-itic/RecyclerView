package com.example.recyclerview.reserves

import java.util.Date

data class Reserva(val idreserva:Int,val idmaterial:Int,val datareserva:Date?,val datafinal:Date?,val descripcio:String)

class ReservesProvider{
    companion object {
        val Reserves:List<Reserva> = listOf(
            Reserva(1,2,"2023-01-01".toDate(),"2023-01-15".toDate(),"Reserva 1"),
            Reserva(1,2,"2023-02-01".toDate(),"2023-01-15".toDate(),"Reserva 2"),
            Reserva(1,2,"2023-03-01".toDate(),null,"Reserva 3"),
            Reserva(1,2,"2023-04-01".toDate(),null,"Reserva 4"),
        )
    }
}