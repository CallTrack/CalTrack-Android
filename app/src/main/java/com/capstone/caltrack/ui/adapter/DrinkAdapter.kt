package com.capstone.caltrack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.caltrack.R
import com.capstone.caltrack.databinding.ItemDrinkBinding

class DrinkAdapter (private val quantity: Int): RecyclerView.Adapter<DrinkAdapter.ListViewHolder>(){
    class ListViewHolder(var binding: ItemDrinkBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.rvIcDrink.setImageResource(R.drawable.ic_baseline_local_drink_24)
        if (position > quantity-1) {
            holder.binding.rvIcDrink.alpha = 0.5F
        } else holder.binding.rvIcDrink.alpha = 1F
    }

    override fun getItemCount(): Int = 8
}