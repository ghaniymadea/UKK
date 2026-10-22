package com.pemula.ukk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemula.ukk.adapter.MonitorAdapter
import com.pemula.ukk.controller.PeminjamanController
import com.pemula.ukk.controller.PengembalianController
import com.pemula.ukk.databinding.ActivityPetugasMonitorBinding
import com.pemula.ukk.model.PengembalianRequest
import com.pemula.ukk.model.PengembalianUnitRequest
import kotlinx.coroutines.launch

class PetugasMonitorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetugasMonitorBinding
    private lateinit var adapter: MonitorAdapter
    private val peminjamanController = PeminjamanController()
    private val pengembalianController = PengembalianController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetugasMonitorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = MonitorAdapter(emptyList()) { item ->
            showReturnDialog(item.id, item.peminjam ?: "User")
        }

        binding.rvMonitor.layoutManager = LinearLayoutManager(this)
        binding.rvMonitor.adapter = adapter

        loadMonitorData()
    }

    private fun loadMonitorData() {
        lifecycleScope.launch {
            val paged = peminjamanController.fetchMonitor(1, 50)
            if (paged != null) {
                adapter.updateData(paged.items)
            }
        }
    }

    private fun showReturnDialog(idPeminjaman: Long, peminjamName: String) {
        AlertDialog.Builder(this)
            .setTitle("Proses Pengembalian: #$idPeminjaman")
            .setMessage("Peminjam: $peminjamName\nProses pengembalian unit barang sekarang?")
            .setPositiveButton("Proses Kembali") { _, _ ->
                processPengembalian(idPeminjaman)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun processPengembalian(idPeminjaman: Long) {
        lifecycleScope.launch {
            val loanDetail = peminjamanController.fetchPeminjamanById(idPeminjaman)
            if (loanDetail != null) {
                val unitRequests = mutableListOf<PengembalianUnitRequest>()
                loanDetail.details.forEach { detail ->
                    detail.idUnitAlat.forEach { unitId ->
                        unitRequests.add(PengembalianUnitRequest(idUnitAlat = unitId, statusKondisi = "baik", catatan = null))
                    }
                }

                val req = PengembalianRequest(
                    idPeminjaman = idPeminjaman,
                    tanggalKembali = "2026-10-26",
                    catatan = "Pengembalian diproses oleh petugas",
                    units = unitRequests
                )

                val success = pengembalianController.createPengembalian(req)
                if (success) {
                    Toast.makeText(this@PetugasMonitorActivity, "Pengembalian berhasil diproses!", Toast.LENGTH_LONG).show()
                    loadMonitorData()
                } else {
                    Toast.makeText(this@PetugasMonitorActivity, "Gagal memproses pengembalian", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@PetugasMonitorActivity, "Gagal memuat detail peminjaman", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
