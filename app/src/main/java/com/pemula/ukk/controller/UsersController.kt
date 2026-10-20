package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.PagedResult
import com.pemula.ukk.model.UserRequest
import com.pemula.ukk.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersController {

    suspend fun fetchUsersList(
        page: Int? = null,
        pageSize: Int? = null,
        search: String? = null,
        idRole: Long? = null
    ): PagedResult<UserResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.usersService.getUsers(
                    page = page,
                    pageSize = pageSize,
                    search = search,
                    idRole = idRole
                )
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchUserById(id: Long): UserResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.usersService.getUserById(id)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun createUser(request: UserRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.usersService.postUsers(request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun updateUser(id: Long, request: UserRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.usersService.putUsers(id, request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun deleteUser(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.usersService.deleteUsers(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
