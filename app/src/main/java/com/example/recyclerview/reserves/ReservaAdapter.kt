package com.example.recyclerview.reserves

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R

class ReservaAdapter(var llistatreserves:List<Reserva>):RecyclerView.Adapter<ReservaAdatperHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaAdatperHolder {
        var iteminflater=LayoutInflater.from(parent.context)
        var recycleritem=iteminflater.inflate(R.layout.reserva_item,parent,false)
        var holder=ReservaAdatperHolder(recycleritem)

        return holder
    }

    override fun getItemCount(): Int {
       return llistatreserves.size
    }

    override fun onBindViewHolder(holder: ReservaAdatperHolder, position: Int) {
        val reserva=llistatreserves.get(position)
        holder.Renderitzar(reserva)
        holder.itemView.setOnClickListener {
            this.reservaClick?.invoke(holder,reserva,position)
        }

        holder.imatgeMaterial.setOnClickListener{
            this.imatgeClick?.invoke(holder,reserva,position)
        }
    }

    private var reservaClick: ((holder:ReservaAdatperHolder,model:Reserva,position:Int) -> Unit)? =null
    public fun setOnReservaClick( reservaClickCallback: (holder: ReservaAdatperHolder,model:Reserva,position:Int)->Unit){
        this.reservaClick=reservaClickCallback
    }

    private var imatgeClick: ((holder:ReservaAdatperHolder,model:Reserva,position:Int) -> Unit)? =null
    public fun setOnImatgeClick( imatgeClickCallback: (holder: ReservaAdatperHolder,model:Reserva,position:Int)->Unit){
        this.imatgeClick=imatgeClickCallback
    }
}

class ReservaAdatperHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imatgeMaterial:ImageView= itemView.findViewById(R.id.item_imatge_material)
    var descripcioReserva:TextView=itemView.findViewById(R.id.item_descripcio_reserva)
    var iniciReserva:TextView=itemView.findViewById(R.id.item_inici_reserva)
    var fiReserva:TextView=itemView.findViewById(R.id.item_fi_reserva)

    public fun Renderitzar(reserva: Reserva){
        descripcioReserva.text=reserva.descripcio
        iniciReserva.text=reserva.datareserva?.toStringFormat("yyyy-MM-dd")
        fiReserva.text=reserva.datafinal?.toStringFormat("yyyy-MM-dd")
    }
}
