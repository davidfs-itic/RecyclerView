package com.example.recyclerview

import Material
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.recyclerview.databinding.ActivityAfegirReservaBinding
import com.example.recyclerview.materials.Bottom_Material
import com.example.recyclerview.materials.MaterialFragment
import com.example.recyclerview.reserves.Reserva
import com.example.recyclerview.reserves.ReservesAPI
import com.example.recyclerview.reserves.toDate
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class AfegirReservaActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAfegirReservaBinding

    private var material=Material(0,"Fes click per triar un material","nothing")
    companion object {

    }

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
        binding.afegirreservaBtnafegir.setOnClickListener(this::afegirReservaiTancar)
        carregarFragment()

    }

    private fun afegirReservaiTancar(view: View?) {
        val novareserva=Reserva(
            0,
            this.material.id,
            3,
            binding.afegirreservaEddatainici.text.toString().toDate("dd/MM/yyyy"),
            binding.afegirreservaEddatafi.text.toString().toDate("dd/MM/yyyy"),
            "",
            ""
        )

        lifecycleScope.launch(Dispatchers.IO) {

            val response=ReservesAPI.API().afegirReserva(novareserva)

            withContext(Dispatchers.Main){
                if (response.isSuccessful ){

                    val resultIntent = Intent()
                    val args = Bundle()
                    args.putParcelable(ARG_RESERVA, novareserva)
                    resultIntent.putExtras(args)
                    setResult(RESULT_OK, resultIntent)
                    this@AfegirReservaActivity.finish() // Tanca l'activitat
                    Log.i( "AfegirReservaActivity","Reserva Afegida correctament")
                }else
                {
                    //Toast.makeText( this,"Error afegint reserva ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Quan es prem el botó de "up", es cridarà el codi següent
                val resultIntent = Intent()
                setResult(RESULT_CANCELED, resultIntent)
                finish() // Tanca l'activitat
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
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


        lifecycleScope.launch(Dispatchers.IO) {

            val response = ReservesAPI.API().llistaMaterials()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){

                    val llistamaterials=response.body().orEmpty()
                    if (llistamaterials.size>0)
                    {
                        val bottomSheet = Bottom_Material(llistamaterials){ materialSeleccionat ->
                            // Acció quan es selecciona un material
                            Toast.makeText(this@AfegirReservaActivity, "Seleccionat: ${materialSeleccionat.descripcio}", Toast.LENGTH_SHORT).show()
                            this@AfegirReservaActivity.material=materialSeleccionat
                            carregarFragment()
                        }
                        bottomSheet.show(supportFragmentManager, "ItemBottomSheet")
                    }else{
                        Toast.makeText(this@AfegirReservaActivity, "Llistat de materials buit", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@AfegirReservaActivity, "Error obtenint materials ${response.errorBody()}", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
    private fun carregarFragment( ){

        val fragment = MaterialFragment.newInstance(this.material)
        // Reemplaza el contenedor actual con el Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.afegirreserva_fragmentMaterial, fragment)
            .commit()
    }
}