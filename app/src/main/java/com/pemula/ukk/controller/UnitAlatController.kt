package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.PagedResult
import com.pemula.ukk.model.UnitAlat
import com.pemula.ukk.model.UnitAlatRequest
import com.pemula.ukk.model.UnitAlatUpdateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UnitAlatController {

    suspend fun fetchUnitAlatList(
        page: Int? = null,
        pageSize: Int? = null,
        idAlat: Long? = null,
        idStatusKetersediaan: Long? = null
    ): PagedResult<UnitAlat>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.unitAlatService.getUnitAlat(
                    page = page,
                    pageSize = pageSize,
                    idAlat = idAlat,
                    idStatusKetersediaan = idStatusKetersediaan
                )
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchUnitAlatById(id: Long): UnitAlat? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.unitAlatService.getUnitAlatById(id)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun createUnitAlat(request: UnitAlatRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.unitAlatService.postUnitAlat(request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun updateUnitAlat(id: Long, request: UnitAlatUpdateRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.unitAlatService.putUnitAlat(id, request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun deleteUnitAlat(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.unitAlatService.deleteUnitAlat(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
