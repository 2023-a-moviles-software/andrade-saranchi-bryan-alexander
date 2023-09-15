package com.example.deber03.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.BCancion
import com.example.deber03.R
import com.example.deber03.SQLiteHelper

class CancionAdapter(private var canciones : List<BCancion>, private var baseDeDatos : SQLiteHelper): RecyclerView.Adapter<CancionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CancionViewHolder(
            layoutInflater.inflate(R.layout.cancion_item,parent,false), baseDeDatos
        )
    }

    override fun getItemCount(): Int = canciones.size

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val item = canciones[position]
        holder.render(item)
    }

    fun actualizarCanciones(nuevasCanciones : List<BCancion>){
        canciones = nuevasCanciones
        notifyDataSetChanged()
    }
}