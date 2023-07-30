package com.example.clonshazam.Adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonshazam.Models.Ranking
import com.example.clonshazam.databinding.RankingCardItemBinding

class RankingViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val binding = RankingCardItemBinding.bind(view)

    fun render(rankingModel : Ranking){
        binding.tvTituloRanking.text = rankingModel.tipoRanking
        binding.tvCancion1Nombre.text =  rankingModel.cancion[0].pista
        binding.tvCancion1Artista.text =  rankingModel.cancion[0].artista
        Glide.with(binding.imvCancion1.context).load(rankingModel.cancion[0].photo).into(binding.imvCancion1)

        binding.tvCancion2Nombre.text =  rankingModel.cancion[1].pista
        binding.tvCancion2Artista.text =  rankingModel.cancion[1].artista
        Glide.with(binding.imvCancion2.context).load(rankingModel.cancion[1].photo).into(binding.imvCancion2)

        binding.tvCancion3Nombre.text =  rankingModel.cancion[2].pista
        binding.tvCancion3Artista.text =  rankingModel.cancion[2].artista
        Glide.with(binding.imvCancion3.context).load(rankingModel.cancion[2].photo).into(binding.imvCancion3)
    }
}