package com.example.recyclerview.materials

import Material
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.recyclerview.R
import com.example.recyclerview.databinding.MaterialItemBinding

class MaterialFragment : Fragment() {

    private var _binding: MaterialItemBinding? = null
    private val binding get() = _binding!!

    // Utilitzem el patró factory per evitar errors al passar dades al Fragment
    //a través del newInstance, que crea el bundle i recuperem en el viewcreated
    //Ho fem així per a que les dades sobrevisquin inclús si el fragment és tornat a crear
    companion object {
        private const val ARG_MATERIAL = "material"

        // Método estático para instanciar el Fragment con datos
        fun newInstance(material: Material): MaterialFragment {
            val fragment = MaterialFragment()
            val args = Bundle()
            args.putParcelable(ARG_MATERIAL, material) // Pasamos el objeto Material
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // sense viewbinding
        //return inflater.inflate(R.layout.material_item, container, false)

        //Amb viewbinding
        _binding = MaterialItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtén el objeto Material desde los argumentos
        val material = arguments?.get(ARG_MATERIAL) as? Material

        if (material != null) {
            _binding!!.materialitemDescripcioMaterial.text= material.descripcio
        }else{
            _binding!!.materialitemDescripcioMaterial.text="Error"
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}