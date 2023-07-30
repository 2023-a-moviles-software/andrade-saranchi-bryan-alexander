package com.example.clonshazam.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonshazam.Models.Cancion
import com.example.clonshazam.databinding.CancionCardItemBinding

class CancionViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val binding = CancionCardItemBinding.bind(view)

    fun render(cancionModel: Cancion){
        binding.tvNombreCancion.text = cancionModel.pista
        binding.tvArtistaCancion.text = cancionModel.artista
        Glide.with(binding.itemImage.context).load(cancionModel.photo).into(binding.itemImage)
    }

}