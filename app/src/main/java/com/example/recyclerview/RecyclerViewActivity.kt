package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
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

class RecyclerViewActivity : AppCompatActivity() {
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
        var rv:RecyclerView=findViewById(R.id.recycler_rvreserves)
        //var llistatreserves=ReservesProvider.Reserves
        var progressbar:ProgressBar=findViewById(R.id.pbwait)
        progressbar.isVisible=true

        rv.layoutManager=LinearLayoutManager(this)

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
        intent.putExtra("message", "Hello from the first activity!")
        startActivity(intent)

    }

    private fun reservaclick(holder: ReservaAdatperHolder, model: Reserva, position:Int):Unit {
        Log.i("ReservaClick","Reserva"+model.idreserva.toString())

    }
    private fun reservaImatgeclick(holder: ReservaAdatperHolder, model: Reserva, position:Int):Unit {
        Log.i("reservaImatgeclick","Reserva"+model.idreserva.toString())
    }
}