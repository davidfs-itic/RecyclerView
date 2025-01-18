package com.example.recyclerview.materials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.reserves.ReservaAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Bottom_Material(
private val items: List<Material>,
private val onItemSelected: (Material) -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_material, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.bottom_material_rvmaterials)
        recyclerView.layoutManager = LinearLayoutManager(context)
        var materialsAdapter= materialsAdapter(items)
        materialsAdapter.setOnMaterialClick(this::materialClicked)
        recyclerView.adapter=materialsAdapter
    }

    private fun materialClicked(materialHolder: MaterialHolder, material: Material, i: Int) {
        onItemSelected(material)
        dismiss()
    }


}