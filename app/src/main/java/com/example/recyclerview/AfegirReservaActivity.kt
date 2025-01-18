package com.example.recyclerview

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.ActivityAfegirReservaBinding
import com.example.recyclerview.materials.Bottom_Material
import com.example.recyclerview.materials.Material
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AfegirReservaActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAfegirReservaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityAfegirReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.afegirreservaToolbar)
        supportActionBar?.title ="Afegir Reserva"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.afegirreservaEscollirmaterial.setOnClickListener(this::mostrarllistamaterial)

        binding.afegirreservaEddatainici.setOnClickListener(this::triardata)
        binding.afegirreservaEddatafi.setOnClickListener(this::triardata)
    }

    private fun triardata(view: View?) {
        // Crear una instancia de MaterialDatePicker
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona una data")
            .build()

        // Mostrar el DatePicker
        datePicker.show(supportFragmentManager, "")

        // Configurar el listener al seleccionar una fecha
        datePicker.addOnPositiveButtonClickListener { selection ->
            // Formatear la fecha seleccionada
            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selection))
            // Actualizar el TextView con la fecha seleccionada
            if (view is EditText) {
                val editText = view as EditText
                editText.setText(selectedDate)
            }
        }
    }

    private fun mostrarllistamaterial(view: View?) {
        val items = listOf<Material>(Material(0,"test1",""),Material (1,"test2",""),Material (3,"test3",""))

        val bottomSheet = Bottom_Material(items){ material ->
            // Acció quan es selecciona un material
            Toast.makeText(this, "Seleccionat: ${material.descripcio}", Toast.LENGTH_SHORT).show()
        }

        bottomSheet.show(supportFragmentManager, "ItemBottomSheet")
    }

}