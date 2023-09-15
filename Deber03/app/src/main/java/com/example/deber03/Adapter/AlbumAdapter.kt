package com.example.deber03.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.BAlbum
import com.example.deber03.R
import com.example.deber03.SQLiteHelper

class AlbumAdapter(private var albums : List<BAlbum>, private val baseDeDatos : SQLiteHelper):RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return AlbumViewHolder(layoutInflater.inflate(R.layout.album_item,parent,false),baseDeDatos)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = albums[position]
        holder.render(item)
    }

    fun actualizar(datos : List<BAlbum>){
        albums = datos
        notifyDataSetChanged()
    }

}