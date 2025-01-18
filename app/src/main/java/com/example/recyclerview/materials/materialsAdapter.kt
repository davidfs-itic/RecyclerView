package com.example.recyclerview.materials

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.MaterialItemBinding
import com.example.recyclerview.reserves.Reserva
import com.example.recyclerview.reserves.ReservaAdatperHolder

class materialsAdapter(var llistatmaterials:List<Material>) :RecyclerView.Adapter<MaterialHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialHolder {

        val binding = MaterialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var holder= MaterialHolder(binding)
        return holder
    }

    override fun getItemCount(): Int {
       return llistatmaterials.size
    }

    override fun onBindViewHolder(holder: MaterialHolder, position: Int) {
        var mat=llistatmaterials.get(position)

        holder.itemView.setOnClickListener{
             this.materialClick?.invoke(holder,mat,position)
        }
        holder.binding.materialitemDescripcioMaterial.text=mat.descripcio
    }
    private var materialClick: ((holder:MaterialHolder, model: Material, position:Int) -> Unit)? =null
    public fun setOnMaterialClick(materialClickCallback: (holder: MaterialHolder, model: Material, position:Int)->Unit){
        this.materialClick=materialClickCallback
    }
}

class MaterialHolder(val binding: MaterialItemBinding):RecyclerView.ViewHolder(binding.root) {

}
