package com.example.clonshazam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clonshazam.Adapter.CancionAdapter
import com.example.clonshazam.Models.BaseDeDatosMemoria
import com.example.clonshazam.databinding.ActivityBibliotecaBinding

class Biblioteca : AppCompatActivity() {
    private lateinit var binding: ActivityBibliotecaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBibliotecaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    fun initRecyclerView(){
        val manager = GridLayoutManager(this,2)
        binding.rvCancionGrid2.layoutManager = manager
        binding.rvCancionGrid2.adapter = CancionAdapter(BaseDeDatosMemoria.cancionesShazameadas)
    }
}