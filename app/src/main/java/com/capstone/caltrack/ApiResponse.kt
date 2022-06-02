package com.capstone.caltrack

import com.google.gson.annotations.SerializedName
import java.util.*

data class ApiResponse(
     @field:SerializedName("status")
     val status: String,

     @field:SerializedName("message")
     val message: String,

     @field:SerializedName("data")
     val data: User
)

data class User(

     @field:SerializedName("id_user")
     var idUser: String?,

     @field:SerializedName("email")
     var email: String?,

     @field:SerializedName("name")
     var name: String?,

     @field:SerializedName("gender")
     var gender: String?,

     @field:SerializedName("age")
     var age: Int?,

     @field:SerializedName("weight")
     var weight: Int?,

     @field:SerializedName("height")
     var height: Int?,

     @field:SerializedName("activity_level")
     var activityLevel: String?,

     @field:SerializedName("daily_calories")
     var dailyCalories: Int?
)

data class Records(

     @field:SerializedName("id_record")
     val idRecord: String,

     @field:SerializedName("id_user")
     val idUser: String,

     @field:SerializedName("date")
     val date: Date,

     @field:SerializedName("calories_in")
     val caloriesIn: Int,

     @field:SerializedName("calories_burn")
     val caloriesBrun: Int,

     @field:SerializedName("total_calories")
     val totalCalories: Int
)

data class Exercise(
     val name: String,
     val map: Boolean,
     val isFavorite: Boolean = false,
)