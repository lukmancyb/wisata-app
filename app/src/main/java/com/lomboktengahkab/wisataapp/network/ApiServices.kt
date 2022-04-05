package com.lomboktengahkab.wisataapp.network

import com.lomboktengahkab.wisataapp.data.BaseResponse
import com.lomboktengahkab.wisataapp.data.Category
import com.lomboktengahkab.wisataapp.data.Destination
import retrofit2.http.*

interface ApiServices {

    @GET("destination/list")
    suspend fun getAllDestination(): BaseResponse<List<Destination>>

    @GET("destination/list-category/{id}")
    suspend fun getDestinationByCategory(@Path("id") id: String): BaseResponse<List<Destination>>

    @GET("destination-category/list")
    suspend fun getAllCategory() : BaseResponse<List<Category>>
}