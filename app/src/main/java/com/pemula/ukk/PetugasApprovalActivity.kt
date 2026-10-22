package com.pemula.ukk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemula.ukk.adapter.ApprovalAdapter
import com.pemula.ukk.controller.PeminjamanController
import com.pemula.ukk.databinding.ActivityPetugasApprovalBinding
import kotlinx.coroutines.launch

class PetugasApprovalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetugasApprovalBinding
    private lateinit var adapter: ApprovalAdapter
    private val peminjamanController = PeminjamanController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetugasApprovalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = ApprovalAdapter(
            emptyList(),
            onApproveClick = { item -> approve(item.id) },
            onRejectClick = { item -> reject(item.id) },
            onBorrowClick = { item -> borrow(item.id) }
        )

        binding.rvApproval.layoutManager = LinearLayoutManager(this)
        binding.rvApproval.adapter = adapter

        loadPeminjaman()
    }

    private fun loadPeminjaman() {
        lifecycleScope.launch {
            val paged = peminjamanController.fetchPeminjamanList(1, 50)
            if (paged != null) {
                adapter.updateData(paged.items)
            }
        }
    }

    private fun approve(id: Long) {
        lifecycleScope.launch {
            val success = peminjamanController.approvePeminjaman(id)
            if (success) {
                Toast.makeText(this@PetugasApprovalActivity, "Peminjaman #$id disetujui", Toast.LENGTH_SHORT).show()
                loadPeminjaman()
            } else {
                Toast.makeText(this@PetugasApprovalActivity, "Gagal menyetujui peminjaman", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun reject(id: Long) {
        lifecycleScope.launch {
            val success = peminjamanController.rejectPeminjaman(id, "Ditolak oleh petugas")
            if (success) {
                Toast.makeText(this@PetugasApprovalActivity, "Peminjaman #$id ditolak", Toast.LENGTH_SHORT).show()
                loadPeminjaman()
            } else {
                Toast.makeText(this@PetugasApprovalActivity, "Gagal menolak peminjaman", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun borrow(id: Long) {
        lifecycleScope.launch {
            val success = peminjamanController.borrowPeminjaman(id)
            if (success) {
                Toast.makeText(this@PetugasApprovalActivity, "Serah terima unit berhasil (dipinjam)", Toast.LENGTH_SHORT).show()
                loadPeminjaman()
            } else {
                Toast.makeText(this@PetugasApprovalActivity, "Gagal serah terima (stok unit tidak cukup / konflik)", Toast.LENGTH_LONG).show()
            }
        }
    }
}
