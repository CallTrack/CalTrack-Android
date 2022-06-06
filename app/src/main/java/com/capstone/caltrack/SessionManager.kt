package com.capstone.caltrack

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    companion object{
        private const val IDUSER = "iduser"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val GENDER = "gender"
        private const val AGE = "age"
        private const val WEIGHT = "weight"
        private const val HEIGHT = "height"
        private const val ACTIVITYLEVEL = "activitylevel"
        private const val DAILYCALORIES = "dailycalories"
    }

    private val preferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun setUser(user: User?) {
        val editor = preferences.edit()
        editor.apply{
            putString(IDUSER, user?.idUser)
            putString(NAME, user?.name)
            putString(EMAIL, user?.email)
            putString(GENDER, user?.gender)
            putInt(AGE, user?.age!!)
            putFloat(WEIGHT, user.weight!!)
            putFloat(HEIGHT, user.height!!)
            putString(ACTIVITYLEVEL, user.activityLevel)
            putInt(DAILYCALORIES, user.dailyCalories!!)
        }
        editor.apply()
    }

    fun getID(): String? {
        return preferences.getString(IDUSER, null)
    }

    fun getUserd(): List<String> {
        return preferences.all.map { it.key }
    }

    fun getUser(): User? {
        val user: User? = null
        user?.email = preferences.getString(EMAIL, null)
        user?.idUser = preferences.getString(IDUSER, null)
        user?.name = preferences.getString(NAME, null)
        user?.gender = preferences.getString(GENDER, null)
        user?.weight = preferences.getFloat(WEIGHT, 0F)
        user?.height = preferences.getFloat(HEIGHT, 0F)
        user?.activityLevel = preferences.getString(ACTIVITYLEVEL, null)
        user?.dailyCalories = preferences.getInt(DAILYCALORIES, 0)
//        preferences.apply {
//            user?.apply {
//                idUser = getString(IDUSER, null)
//                name = getString(NAME, null)
//                email = getString(EMAIL, null)
//                gender = getString(GENDER, null)
//                age = getInt(AGE, 0)
//                weight = getFloat(WEIGHT, 0F)
//                height = getFloat(HEIGHT, 0F)
//                activityLevel = getString(ACTIVITYLEVEL, null)
//                dailyCalories = getInt(DAILYCALORIES, 0)
//
//            }
//        }
        return user
    }
}