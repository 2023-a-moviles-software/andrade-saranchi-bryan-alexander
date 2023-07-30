package com.example.clonshazam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clonshazam.Adapter.RankingAdapter
import com.example.clonshazam.Models.BaseDeDatosMemoria
import com.example.clonshazam.databinding.ActivityRankingBinding

class RankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        binding.rvRankings.layoutManager = manager
        binding.rvRankings.adapter = RankingAdapter(BaseDeDatosMemoria.rankings)



    }
}