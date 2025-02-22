package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.fastapiresponse.Error400Response
import com.example.recyclerview.fastapiresponse.Error422Response
import com.example.recyclerview.login.LoginAPI
import com.example.recyclerview.login.Usuari
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mainBtnLogin.setOnClickListener(this::doLogin)
        binding.mainBtnRegister.setOnClickListener(this::goRegister)
        /*var button:Button=findViewById(R.id.main_btnllistat)
        button.setOnClickListener(this::btn_llistat_click)*/
        binding.mainProgressbar.visibility=View.GONE

    }

    private fun goRegister(view: View?) {
        val i = Intent(this, ActivityRegistre::class.java)
        startActivity(i)
    }

    private fun doLogin(view: View?) {
        binding.mainProgressbar.visibility= View.VISIBLE
        var usuari = Usuari(
            nom_usuari = "",
            email = binding.email.text.toString(),
            contrassenya = binding.contrassenya.text.toString(),
        )

        lifecycleScope.launch(Dispatchers.Main) {

            var result = withContext(Dispatchers.IO) {
                LoginAPI.API().login(usuari)
            }
            Log.i("",result.raw().message)


            if (result.isSuccessful) {
                val i = Intent(this@MainActivity, RecyclerViewActivity::class.java)
                startActivity(i)
            } else {
                var errorBody=result.errorBody()?.string()

                var errorMsg=result.raw().message
                if (result.raw().code  == 400) {
                    errorMsg = Gson().fromJson(errorBody, Error400Response::class.java).detail

                } else if (result.raw().code  == 422) {
                    // Error del servidor
                    errorMsg = Gson().fromJson(errorBody, Error422Response::class.java).detail.get(0).msg
                }

                Toast.makeText(this@MainActivity,errorMsg, Toast.LENGTH_SHORT).show()
                binding.email.setText("")
                binding.contrassenya.setText("")
            }
            binding.mainProgressbar.visibility= View.GONE
        }
    }


}
