package com.capstone.caltrack.ui.exercise

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.caltrack.R
import com.capstone.caltrack.data.local.entity.ExerciseEntity
import com.capstone.caltrack.databinding.ItemExerciseBinding

class ExerciseAdapter(private val onBookmarkClick: (ExerciseEntity) -> Unit): ListAdapter<ExerciseEntity, ExerciseAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemExerciseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: ExerciseEntity) {
            binding.tvExercise.text = exercise.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.binding.tvExercise.text = exercise.name

        val ivBookmark = holder.binding.ivBookmark
        if (exercise.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_baseline_star_35))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_baseline_star_outline_35))
        }

        ivBookmark.setOnClickListener {
            onBookmarkClick(exercise)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ExerciseEntity> =
            object : DiffUtil.ItemCallback<ExerciseEntity>() {
                override fun areItemsTheSame(oldExercise: ExerciseEntity, newExercise: ExerciseEntity): Boolean {
                    return oldExercise.name == newExercise.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldExercise: ExerciseEntity, newExercise: ExerciseEntity): Boolean {
                    return oldExercise == newExercise
                }
            }
    }
}