package com.example.codingchallenge.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codingchallenge.models.StockTicker

class StockListAdapter: RecyclerView.Adapter<StockListAdapter.StockViewHolder>() {
    private val data = ArrayList<StockTicker>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class StockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}