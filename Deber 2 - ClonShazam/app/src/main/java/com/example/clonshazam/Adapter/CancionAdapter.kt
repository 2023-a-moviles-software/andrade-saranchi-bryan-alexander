package com.example.clonshazam.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clonshazam.Models.Cancion
import com.example.clonshazam.R

class CancionAdapter(private val cancionesList : ArrayList<Cancion>) : RecyclerView.Adapter<CancionViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CancionViewHolder(layoutInflater.inflate(R.layout.cancion_card_item, parent,false))
    }

    override fun getItemCount(): Int = cancionesList.size


    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val item = cancionesList[position]
        holder.render(item)
    }

}