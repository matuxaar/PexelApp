package com.example.pexelapp.data.network

import com.example.pexelapp.data.models.PhotoResponse
import com.example.pexelapp.data.models.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PhotoService {

    @GET("search")
    suspend fun search(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String
    ): Response

    @GET("curated")
    suspend fun getCurated(
        @QueryMap params: Map<String, Int>
    ): Response

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: Int
    ): PhotoResponse
}