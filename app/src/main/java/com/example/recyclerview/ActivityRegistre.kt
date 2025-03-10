package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.databinding.ActivityRegistreBinding

class ActivityRegistre : AppCompatActivity() {

    lateinit var binding: ActivityRegistreBinding
    private val model:ActivityRegistreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registreProgressbar.visibility=View.GONE
        binding.regBtnregistre.setOnClickListener(this::registrarusuari)

        model.validaciodades.observe(this){Estat ->
            binding.regNomusuari.setError(Estat.errorNomUsuari)
        }


    }

    private fun registrarusuari(view: View?) {
       model.registrarusuari()
    }
}