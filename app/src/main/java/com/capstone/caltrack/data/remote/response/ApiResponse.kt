package com.capstone.caltrack.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ApiResponse(
     @field:SerializedName("status")
     val status: String,

     @field:SerializedName("message")
     val message: String,

     @field:SerializedName("data")
     val data: User,

     @field:SerializedName("foodList")
     val foodList: List<Food>,

     @field:SerializedName("exerciseList")
     val exerciseList: List<Exercise>,

     @field:SerializedName("recordList")
     val recordList: List<Records>
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
     var weight: Float?,

     @field:SerializedName("height")
     var height: Float?,

     @field:SerializedName("activity_level")
     var activityLevel: String?,

     @field:SerializedName("daily_calories")
     var dailyCalories: Int?
)

@Parcelize
data class Food(

     @field:SerializedName("id_food")
     val idFood: Int,

     @field:SerializedName("name")
     val name: String,

     @field:SerializedName("calories_per_serving")
     val calories: Int
) : Parcelable

data class Records(

     @field:SerializedName("id_record")
     val idRecord: String,

     @field:SerializedName("id_user")
     val idUser: String,

     @field:SerializedName("date")
     val date: String,

     @field:SerializedName("calories_in")
     val caloriesIn: Int,

     @field:SerializedName("calories_burn")
     val caloriesBurn: Int,

     @field:SerializedName("total_calories")
     val totalCalories: Int
)

data class Exercise(

     @field:SerializedName("id_exercise")
     val id_exercise: Int,

     @field:SerializedName("name")
     val name: String,

     @field:SerializedName("calories_per_minute")
     val caloriesPerMinute: Int
)