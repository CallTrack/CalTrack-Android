package com.capstone.caltrack

import android.content.Context

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
            putInt(WEIGHT, user.weight!!)
            putInt(HEIGHT, user.height!!)
            putString(ACTIVITYLEVEL, user.activityLevel)
            putInt(DAILYCALORIES, user.dailyCalories!!)
        }
        editor.apply()
    }

    fun getUser(): User? {
        val user: User? = null
        preferences.apply {
            user?.apply {
                idUser = getString(IDUSER, null)
                name = getString(NAME, null)
                email = getString(EMAIL, null)
                gender = getString(GENDER, null)
                age = getInt(AGE, 0)
                weight = getInt(WEIGHT, 0)
                height = getInt(HEIGHT, 0)
                activityLevel = getString(ACTIVITYLEVEL, null)
                dailyCalories = getInt(DAILYCALORIES, 0)

            }
        }
        return user
    }
}