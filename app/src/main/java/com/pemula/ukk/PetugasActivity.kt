package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pemula.ukk.controller.LaporanController
import com.pemula.ukk.controller.PeminjamanController
import com.pemula.ukk.databinding.ActivityPetugasBinding
import com.pemula.ukk.utils.SessionManager
import kotlinx.coroutines.launch

class PetugasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetugasBinding
    private lateinit var sessionManager: SessionManager
    private val peminjamanController = PeminjamanController()
    private val laporanController = LaporanController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        val userName = sessionManager.getUserName() ?: "Petugas"
        binding.tvPetugasStatus.text = "Status: Login sebagai Petugas ($userName)"

        binding.btnLogoutPetugas.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnApprovalPeminjaman.setOnClickListener {
            lifecycleScope.launch {
                val paged = peminjamanController.fetchPeminjamanList(status = "diajukan")
                val count = paged?.total ?: 0
                showAlert("Approval Peminjaman (GET /api/v1/peminjaman?status=diajukan)", "Jumlah pengajuan pinjam baru (status 'diajukan'): $count\n(Petugas dapat menyetujui, menolak, atau melakukan serah terima unit/borrow).")
            }
        }

        binding.btnMonitorPeminjaman.setOnClickListener {
            lifecycleScope.launch {
                val paged = peminjamanController.fetchMonitor()
                val count = paged?.total ?: 0
                showAlert("Monitoring Peminjaman (GET /api/v1/peminjaman/monitor)", "Jumlah peminjaman jatuh tempo/terlambat: $count\n(Menampilkan daftar peminjaman aktif yang perlu dimonitor).")
            }
        }

        binding.btnLaporan.setOnClickListener {
            lifecycleScope.launch {
                laporanController.fetchLaporanPeminjaman()
                showAlert("Laporan Peminjaman & Pengembalian", "Laporan Peminjaman & Pengembalian berhasil dimuat dari API.\n(Petugas dapat melihat ringkasan laporan dan mengekspor ke format JSON/CSV).")
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
