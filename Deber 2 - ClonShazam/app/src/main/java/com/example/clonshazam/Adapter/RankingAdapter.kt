package com.example.clonshazam.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clonshazam.Models.Ranking
import com.example.clonshazam.R

class RankingAdapter(private val rankingList: ArrayList<Ranking>):RecyclerView.Adapter<RankingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RankingViewHolder(layoutInflater.inflate(R.layout.ranking_card_item,parent,false))
    }

    override fun getItemCount(): Int = rankingList.size

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val item = rankingList[position]
        holder.render(item)
    }

}