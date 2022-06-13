package com.capstone.caltrack.data.local

import android.content.Context
import com.capstone.caltrack.R
import com.capstone.caltrack.data.remote.response.User

class SessionManager (context: Context) {
    companion object{
        private const val IDUSER = "iduser"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val GENDER = "gender"
        private const val AGE = "age"
        private const val WEIGHT = "weight"
        private const val HEIGHT = "height"
        private const val ACTIVITY_LEVEL = "activitylevel"
        private const val DAILY_CALORIES = "dailycalories"
        private const val DRINK = "drink"
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
            putString(ACTIVITY_LEVEL, user.activityLevel)
            putInt(DAILY_CALORIES, user.dailyCalories!!)
        }
        editor.apply()
    }

    fun setDrink(qty: Int) {
        val editor = preferences.edit()
        editor.putInt(DRINK, qty)
        editor.apply()
    }

    fun getDrink(): Int {
        return preferences.getInt(DRINK, 0)
    }

    fun getID(): String? {
        return preferences.getString(IDUSER, null)
    }

    fun getWeight(): String {
        return preferences.getFloat(WEIGHT, 0F).toString()
    }

    fun getHeight(): String {
        return preferences.getFloat(HEIGHT, 0F).toString()
    }

    fun getActive(): String? {
        return preferences.getString(ACTIVITY_LEVEL, null)
    }

    fun getDailyCalories(): Int {
        return preferences.getInt(DAILY_CALORIES, 2000)
    }

    fun getUser(): User? {
        val user: User? = null
        user?.email = preferences.getString(EMAIL, null)
        user?.idUser = preferences.getString(IDUSER, null)
        user?.name = preferences.getString(NAME, null)
        user?.gender = preferences.getString(GENDER, null)
        user?.weight = preferences.getFloat(WEIGHT, 0F)
        user?.height = preferences.getFloat(HEIGHT, 0F)
        user?.activityLevel = preferences.getString(ACTIVITY_LEVEL, null)
        user?.dailyCalories = preferences.getInt(DAILY_CALORIES, 0)
        return user
    }

    fun logout(){
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}