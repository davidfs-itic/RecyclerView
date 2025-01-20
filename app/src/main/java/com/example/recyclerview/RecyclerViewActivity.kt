package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.recyclerview.reserves.Reserva
import com.example.recyclerview.reserves.ReservaAdapter
import com.example.recyclerview.reserves.ReservaAdatperHolder
import com.example.recyclerview.reserves.ReservesAPI
import com.example.recyclerview.reserves.ReservesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


public const val ARG_RESERVA = "material"

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var rv:RecyclerView
    private lateinit var progressbar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var btnafgir=findViewById<ImageButton>(R.id.recycler_afegirReserva)
        btnafgir.setOnClickListener(this::afegirReservaclick)
        rv=findViewById(R.id.recycler_rvreserves)
        //var llistatreserves=ReservesProvider.Reserves
        progressbar=findViewById(R.id.pbwait)


        rv.layoutManager=LinearLayoutManager(this)

        actualitzaReserves()
    }

    private fun actualitzaReserves() {
        progressbar.isVisible=true
        var service= ReservesAPI.API()
        lifecycleScope.launch(Dispatchers.IO) {
            val llistatreservesapi=service.llistaReserves(3)

            Log.i("recyclerview",llistatreservesapi.size.toString())
            withContext(Dispatchers.Main) {
                var reservaadapter=ReservaAdapter(llistatreservesapi)
                reservaadapter.setOnReservaClick( this@RecyclerViewActivity::reservaclick)
                reservaadapter.setOnImatgeClick(this@RecyclerViewActivity::reservaImatgeclick)
                rv.adapter=reservaadapter
                progressbar.isVisible=false
            }
        }
    }

    private fun afegirReservaclick(view: View?) {
        val intent = Intent(this, AfegirReservaActivity::class.java)
        //intent.putExtra("message", "Hello from the first activity!")
        //startActivity(intent)
        afegirReservaLauncher.launch(intent)
    }

    private val afegirReservaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val reservaAfegida = result.data?.extras?.getParcelable<Reserva>(ARG_RESERVA)
            if (reservaAfegida !=null) {
                Toast.makeText(this, "Reserva afegida correctament", Toast.LENGTH_SHORT).show()
                this.actualitzaReserves()
            }
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "No s'ha afegit cap reserva", Toast.LENGTH_SHORT).show()
        }
    }
    private fun reservaclick(holder: ReservaAdatperHolder, model: Reserva, position:Int):Unit {
        Log.i("ReservaClick","Reserva"+model.idreserva.toString())

    }
    private fun reservaImatgeclick(holder: ReservaAdatperHolder, model: Reserva, position:Int):Unit {
        Log.i("reservaImatgeclick","Reserva"+model.idreserva.toString())
    }
}