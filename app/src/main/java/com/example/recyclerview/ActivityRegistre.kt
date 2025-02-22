package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.databinding.ActivityRegistreBinding

class ActivityRegistre : AppCompatActivity() {

    lateinit var binding: ActivityRegistreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registreProgressbar.visibility=View.GONE
        binding.regBtnregistre.setOnClickListener(this::registrarusuari)

    }

    private fun registrarusuari(view: View?) {
        TODO("Not yet implemented")
    }
}