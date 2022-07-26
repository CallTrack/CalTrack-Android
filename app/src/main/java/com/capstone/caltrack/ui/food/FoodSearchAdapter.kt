package com.capstone.caltrack.ui.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.caltrack.R
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.databinding.ItemFoodSearchBinding

class FoodSearchAdapter(private val food: List<FoodEntity>): RecyclerView.Adapter<FoodSearchAdapter.MyViewHolder>(){

//    private var listSelected = mutableListOf<FoodEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class MyViewHolder(val binding: ItemFoodSearchBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFoodSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val food = food[position]
        holder.binding.apply {
            if (food.selected) check.visibility = View.VISIBLE
            else check.visibility = View.GONE
            tvSearchFood.text = food.name
            tvSearchCal.text = holder.itemView.context.getString(R.string.calorie_val, food.calories)
            itemFoodSearch.setOnClickListener {
                selectItem(position)
                onItemClickCallback.onItemClicked(food)
            }
        }
    }

    override fun getItemCount(): Int = food.size

    fun selectItem(index: Int) {
        if (food[index].selected) {
            food[index].selected = false
        } else {
            food[index].selected = true
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(OnItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = OnItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FoodEntity)
    }
}