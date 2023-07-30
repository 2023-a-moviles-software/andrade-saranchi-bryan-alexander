package com.example.clonshazam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clonshazam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imgBiblioteca
            .setOnClickListener {
                irActividad(Biblioteca::class.java)
            }
        binding.imgRanking
            .setOnClickListener {
                irActividad(RankingActivity::class.java)
            }

    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }
}