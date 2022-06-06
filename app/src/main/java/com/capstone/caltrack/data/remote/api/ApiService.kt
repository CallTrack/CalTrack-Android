package com.capstone.caltrack.data.remote.api

import com.capstone.caltrack.ApiResponse
import com.capstone.caltrack.Records
import com.capstone.caltrack.User
import retrofit2.http.*
import java.util.*

interface ApiService {
//    @FormUrlEncoded
//    @POST("users")
//    suspend fun register(
//        @Field("email") email: String,
//        @Field("name") name: String,
//        @Field("gender") gender: String,
//        @Field("age") age: Int,
//        @Field("weight") weight: Float,
//        @Field("height") height: Float,
//        @Field("activityLevel") activityLevel: String
//    ): ApiResponse

    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("weight") weight: Float,
        @Field("height") height: Float,
        @Field("activityLevel") activityLevel: String
    ): ApiResponse

//    @FormUrlEncoded
//    @GET("users/{email}")
//    suspend fun login(
//        @Path("email") email: String
//    ): User

    @GET("users/{email}")
    suspend fun login(
        @Path("email") email: String
    ): User

    @FormUrlEncoded
    @PUT("users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Field("name") name: String,
        @Field("age") age: Int,
        @Field("weight") weight: Float,
        @Field("height") height: Float,
        @Field("activityLevel") activityLevel: String
    ): ApiResponse

    @FormUrlEncoded
    @POST("records/{userId}")
    suspend fun addRecord(
        @Path("userId") userId: String,
        @Field("date") date: Date,
        @Field("caloriesIn") caloriesIn: Int,
        @Field("caloriesBurn") caloriesBurn: Int,
        @Field("totalCalories") totalCalories: Int
    ): ApiResponse

    @GET("records/{userId}")
    suspend fun getRecord(
        @Path("userId") userId: String
    ): Records

}