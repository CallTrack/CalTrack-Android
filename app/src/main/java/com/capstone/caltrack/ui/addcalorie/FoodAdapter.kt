package com.capstone.caltrack.ui.addcalorie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.caltrack.R
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.databinding.ItemFoodBinding

class FoodAdapter(private val food: List<FoodEntity>): RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val food = food[position]
        holder.binding.apply {
            tvFoodName.text = food.name
            tvCal.text = holder.itemView.context.getString(R.string.calorie_val, food.calories)
        }
    }

    override fun getItemCount(): Int = food.size
}