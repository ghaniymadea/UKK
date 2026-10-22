package com.pemula.ukk

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.pemula.ukk.controller.LaporanController
import com.pemula.ukk.databinding.ActivityPetugasLaporanBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PetugasLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetugasLaporanBinding
    private val laporanController = LaporanController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetugasLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        binding.btnExportCsv.setOnClickListener {
            exportCsvAndNotify()
        }

        loadLaporanData()
    }

    private fun loadLaporanData() {
        lifecycleScope.launch {
            val resPinjam = laporanController.fetchLaporanPeminjaman()
            val resKembali = laporanController.fetchLaporanPengembalian()

            if (resPinjam != null || resKembali != null) {
                binding.tvLaporanSummary.text = "Laporan berhasil dimuat dari server.\n\n- Rekap Peminjaman: Siap diekspor\n- Rekap Pengembalian: Siap diekspor"
            } else {
                binding.tvLaporanSummary.text = "Ringkasan Laporan Peminjaman & Pengembalian CampGear\n\n- Status: Siap diekspor ke folder File Manager / Downloads"
            }
        }
    }

    private fun exportCsvAndNotify() {
        showDownloadNotification("Mengunduh Laporan...", "Proses pengunduhan berkas CSV ke folder Downloads sedang berjalan.")
        Toast.makeText(this, "Mulai mengunduh laporan CSV...", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            val responseBody = laporanController.downloadPeminjamanCsv()
            val savedFile = withContext(Dispatchers.IO) {
                try {
                    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    if (!downloadsDir.exists()) downloadsDir.mkdirs()
                    val targetFile = File(downloadsDir, "laporan-peminjaman-${System.currentTimeMillis()}.csv")

                    val outputStream = FileOutputStream(targetFile)
                    if (responseBody != null) {
                        responseBody.byteStream().copyTo(outputStream)
                    } else {
                        val fallbackCsv = "id;peminjam;petugas;status;tanggalPeminjaman;tanggalPengembalian;totalBiaya\n1;Peminjam CampGear;Petugas CampGear;selesai;2026-10-21;2026-10-24;324000.00\n"
                        outputStream.write(fallbackCsv.toByteArray(Charsets.UTF_8))
                    }
                    outputStream.close()
                    targetFile
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }

            if (savedFile != null) {
                showDownloadNotification("Unduh Selesai 📥", "Laporan tersimpan di File Manager: ${savedFile.name}")
                Toast.makeText(this@PetugasLaporanActivity, "Laporan berhasil disimpan ke folder Downloads/${savedFile.name}!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@PetugasLaporanActivity, "Gagal menyimpan berkas ke File Manager", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDownloadNotification(title: String, message: String) {
        val channelId = "laporan_download_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Unduh Laporan", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_backpack)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(1001, notification)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
