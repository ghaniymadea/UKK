package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface UsersServices {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("search") search: String? = null,
        @Query("idRole") idRole: Long? = null
    ): Response<PagedResult<UserResponse>>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Long
    ): Response<UserResponse>

    @POST("users")
    suspend fun postUsers(
        @Body request: UserRequest
    ): Response<Unit>

    @PUT("users/{id}")
    suspend fun putUsers(
        @Path("id") id: Long,
        @Body request: UserRequest
    ): Response<Unit>

    @DELETE("users/{id}")
    suspend fun deleteUsers(
        @Path("id") id: Long
    ): Response<Unit>
}
