package com.capstone.caltrack.data.remote.api

import com.capstone.caltrack.data.remote.response.Food
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CensorService {

    @Multipart
    @POST("predict")
    suspend fun predictImage(
        @Part file: MultipartBody.Part
    ): Food
}