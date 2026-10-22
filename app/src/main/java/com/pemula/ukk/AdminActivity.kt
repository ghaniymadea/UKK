package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pemula.ukk.controller.*
import com.pemula.ukk.databinding.ActivityAdminBinding
import com.pemula.ukk.utils.SessionManager
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var sessionManager: SessionManager
    private val alatController = AlatController()
    private val kategoriController = KategoriController()
    private val unitAlatController = UnitAlatController()
    private val usersController = UsersController()
    private val logController = LogAktivitasController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        val userName = sessionManager.getUserName() ?: "Admin"
        binding.tvAdminStatus.text = "Status: Login sebagai Admin ($userName)"

        binding.btnLogoutAdmin.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnManageAlat.setOnClickListener {
            lifecycleScope.launch {
                val paged = alatController.fetchAlatList(1, 10)
                val count = paged?.total ?: 0
                showAlert("Kelola Alat (GET /api/v1/alat)", "Total alat terdaftar: $count\n(Admin dapat menambah, mengubah, atau menghapus alat dengan upload gambar maks 2MB).")
            }
        }

        binding.btnManageKategori.setOnClickListener {
            lifecycleScope.launch {
                val list = kategoriController.fetchKategoriList()
                val names = list.joinToString(", ") { it.nama }
                showAlert("Kelola Kategori (GET /api/v1/kategori)", "Daftar Kategori: $names\n(Admin dapat menambah/mengubah kategori).")
            }
        }

        binding.btnManageUnit.setOnClickListener {
            lifecycleScope.launch {
                val paged = unitAlatController.fetchUnitAlatList(1, 10)
                val count = paged?.total ?: 0
                showAlert("Kelola Unit Alat (GET /api/v1/unit-alat)", "Total unit inventaris: $count\n(Mengelola kode inventaris dan status barang/ketersediaan).")
            }
        }

        binding.btnManageUsers.setOnClickListener {
            lifecycleScope.launch {
                val paged = usersController.fetchUsersList(1, 10)
                val count = paged?.total ?: 0
                showAlert("Kelola User (GET /api/v1/users)", "Total user terdaftar: $count\n(Admin dapat mengelola data pengguna, role Admin/Petugas/Peminjam).")
            }
        }

        binding.btnViewLogs.setOnClickListener {
            lifecycleScope.launch {
                val paged = logController.fetchLogAktivitasList(1, 10)
                val count = paged?.total ?: 0
                showAlert("Log Aktivitas Sistem (GET /api/v1/log-aktivitas)", "Total log aktivitas: $count\n(Menampilkan rekam jejak aktivitas sistem dan pengguna).")
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tutup", null)
            .show()
    }
}
