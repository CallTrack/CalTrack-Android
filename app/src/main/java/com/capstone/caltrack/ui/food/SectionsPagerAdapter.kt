package com.capstone.caltrack.ui.food

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.caltrack.ui.food.FoodCustomFragment
import com.capstone.caltrack.ui.food.FoodSearchFragment

class SectionsPagerAdapter internal constructor(activity: AppCompatActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FoodSearchFragment()
            1 -> fragment = FoodCustomFragment()
        }
        return fragment as Fragment
    }
}