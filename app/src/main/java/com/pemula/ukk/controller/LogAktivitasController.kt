package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.LogAktivitas
import com.pemula.ukk.model.PagedResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogAktivitasController {

    suspend fun fetchLogAktivitasList(
        page: Int? = null,
        pageSize: Int? = null,
        idUser: Long? = null,
        from: String? = null,
        to: String? = null
    ): PagedResult<LogAktivitas>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.logAktivitasService.getLogAkativitas(
                    page = page,
                    pageSize = pageSize,
                    idUser = idUser,
                    from = from,
                    to = to
                )
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
