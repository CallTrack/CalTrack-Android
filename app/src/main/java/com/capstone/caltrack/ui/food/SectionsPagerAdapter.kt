package com.capstone.caltrack.ui.food

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter internal constructor(activity: AppCompatActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    var mealTime: String = ""

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FoodSearchFragment()
            1 -> fragment = FoodCustomFragment()
        }
        fragment?.arguments = Bundle().apply {
            putString(FoodSearchFragment.ARG_TIME, mealTime)
        }
        return fragment as Fragment
    }
}