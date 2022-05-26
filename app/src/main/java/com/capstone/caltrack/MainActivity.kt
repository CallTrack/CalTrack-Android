package com.capstone.caltrack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.capstone.caltrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navView = binding.bottomNavigationView

//        navView.menu.getItem(1).isEnabled = false

//        supportFragmentManager.beginTransaction().replace(R.id.framecontainer, MainFragment()).commit()

//        navView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
//            var temp: Fragment? = null
//            when(item.itemId) {
//                R.id.nav_home -> {
//                    temp = fragment_main()
//                    true
//                }
//                R.id.nav_profile -> {
//                    temp = fragment_profile()
//                    true
//                }
//                else -> false
//            }
//
//            if (temp != null) {
//                supportFragmentManager.beginTransaction().replace(R.id.framecontainer, temp).commit()
//            }
//        })
//        navView.setOnItemSelectedListener { item ->
//            when(item.itemId){
//                R.id.nav_home -> {
//
//                }
//            }
//        }
    }
}