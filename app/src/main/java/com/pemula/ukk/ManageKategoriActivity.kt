package com.pemula.ukk

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemula.ukk.adapter.KategoriAdapter
import com.pemula.ukk.controller.KategoriController
import com.pemula.ukk.databinding.ActivityManageKategoriBinding
import com.pemula.ukk.databinding.DialogManageKategoriBinding
import com.pemula.ukk.model.KategoriRequest
import kotlinx.coroutines.launch

class ManageKategoriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageKategoriBinding
    private lateinit var adapter: KategoriAdapter
    private val kategoriController = KategoriController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = KategoriAdapter(emptyList()) { kategori ->
            showOptionsDialog(kategori.id, kategori.nama)
        }

        binding.rvKategori.layoutManager = LinearLayoutManager(this)
        binding.rvKategori.adapter = adapter

        binding.btnAddKategori.setOnClickListener {
            showAddKategoriDialog()
        }

        loadKategori()
    }

    private fun loadKategori() {
        lifecycleScope.launch {
            val list = kategoriController.fetchKategoriList()
            adapter.updateData(list)
        }
    }

    private fun showAddKategoriDialog() {
        val dBinding = DialogManageKategoriBinding.inflate(layoutInflater)
        dBinding.tvDialogTitle.text = "Tambah Kategori Baru"

        val dialog = AlertDialog.Builder(this)
            .setView(dBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        dBinding.btnDialogCancel.setOnClickListener { dialog.dismiss() }
        dBinding.btnDialogSave.setOnClickListener {
            val name = dBinding.etDialogKategoriNama.text.toString().trim()
            if (name.isNotEmpty()) {
                lifecycleScope.launch {
                    val success = kategoriController.createKategori(KategoriRequest(nama = name))
                    if (success) {
                        Toast.makeText(this@ManageKategoriActivity, "Kategori berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        loadKategori()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this@ManageKategoriActivity, "Gagal menambah kategori", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        dialog.show()
    }

    private fun showOptionsDialog(id: Long, currentName: String) {
        val options = arrayOf("Edit Kategori", "Hapus Kategori")
        AlertDialog.Builder(this)
            .setTitle("Opsi Kategori: $currentName")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditKategoriDialog(id, currentName)
                    1 -> deleteKategori(id)
                }
            }
            .show()
    }

    private fun showEditKategoriDialog(id: Long, currentName: String) {
        val dBinding = DialogManageKategoriBinding.inflate(layoutInflater)
        dBinding.tvDialogTitle.text = "Edit Kategori"
        dBinding.etDialogKategoriNama.setText(currentName)

        val dialog = AlertDialog.Builder(this)
            .setView(dBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        dBinding.btnDialogCancel.setOnClickListener { dialog.dismiss() }
        dBinding.btnDialogSave.setOnClickListener {
            val newName = dBinding.etDialogKategoriNama.text.toString().trim()
            if (newName.isNotEmpty()) {
                lifecycleScope.launch {
                    val success = kategoriController.updateKategori(id, KategoriRequest(nama = newName))
                    if (success) {
                        Toast.makeText(this@ManageKategoriActivity, "Kategori diperbarui", Toast.LENGTH_SHORT).show()
                        loadKategori()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this@ManageKategoriActivity, "Gagal memperbarui kategori", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        dialog.show()
    }

    private fun deleteKategori(id: Long) {
        lifecycleScope.launch {
            val success = kategoriController.deleteKategori(id)
            if (success) {
                Toast.makeText(this@ManageKategoriActivity, "Kategori berhasil dihapus", Toast.LENGTH_SHORT).show()
                loadKategori()
            } else {
                Toast.makeText(this@ManageKategoriActivity, "Gagal menghapus (kategori digunakan oleh alat)", Toast.LENGTH_LONG).show()
            }
        }
    }
}
