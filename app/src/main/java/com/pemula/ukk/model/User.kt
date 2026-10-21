package com.pemula.ukk.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(value = "id", alternate = ["Id"])
    val id: Long,
    @SerializedName(value = "idRole", alternate = ["IdRole", "id_role"])
    val idRole: Long,
    @SerializedName(value = "role", alternate = ["Role"])
    val role: String? = null,
    @SerializedName(value = "idJenisKelamin", alternate = ["IdJenisKelamin", "id_jenis_kelamin"])
    val idJenisKelamin: Long? = null,
    @SerializedName(value = "jenisKelamin", alternate = ["JenisKelamin", "jenis_kelamin"])
    val jenisKelamin: String? = null,
    @SerializedName(value = "nama", alternate = ["Nama", "name", "username"])
    val nama: String,
    @SerializedName(value = "noTelepon", alternate = ["NoTelepon", "phone", "no_telepon", "telepon", "no_telp"])
    val noTelepon: String?,
    @SerializedName(value = "alamat", alternate = ["Alamat", "address", "jalan", "lokasi", "alamatLengkap", "alamat_lengkap", "alamat_user"])
    val alamat: String?,
    @SerializedName(value = "createdAt", alternate = ["CreatedAt", "created_at"])
    val createdAt: String? = null
)
