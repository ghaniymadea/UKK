package com.pemula.ukk

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemula.ukk.adapter.UnitAdapter
import com.pemula.ukk.controller.UnitAlatController
import com.pemula.ukk.databinding.ActivityManageUnitBinding
import com.pemula.ukk.databinding.DialogManageUnitBinding
import com.pemula.ukk.model.UnitAlat
import com.pemula.ukk.model.UnitAlatRequest
import kotlinx.coroutines.launch

class ManageUnitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageUnitBinding
    private lateinit var adapter: UnitAdapter
    private val unitController = UnitAlatController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageUnitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = UnitAdapter(emptyList()) { unit ->
            showOptionsDialog(unit)
        }

        binding.rvUnit.layoutManager = LinearLayoutManager(this)
        binding.rvUnit.adapter = adapter

        binding.btnAddUnit.setOnClickListener {
            showAddUnitDialog()
        }

        loadUnits()
    }

    private fun loadUnits() {
        lifecycleScope.launch {
            val paged = unitController.fetchUnitAlatList(1, 50)
            if (paged != null) {
                adapter.updateData(paged.items)
            }
        }
    }

    private fun showAddUnitDialog() {
        val dBinding = DialogManageUnitBinding.inflate(layoutInflater)
        dBinding.tvDialogTitle.text = "Tambah Unit Inventaris Baru"

        val dialog = AlertDialog.Builder(this)
            .setView(dBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        dBinding.btnDialogCancel.setOnClickListener { dialog.dismiss() }
        dBinding.btnDialogSave.setOnClickListener {
            val idAlat = dBinding.etDialogUnitIdAlat.text.toString().toLongOrNull() ?: 1L
            val kode = dBinding.etDialogUnitKode.text.toString().trim()

            if (kode.isNotEmpty()) {
                lifecycleScope.launch {
                    val req = UnitAlatRequest(idAlat = idAlat, kodeInventaris = kode, idStatusBarang = 1)
                    val success = unitController.createUnitAlat(req)
                    if (success) {
                        Toast.makeText(this@ManageUnitActivity, "Unit berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        loadUnits()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this@ManageUnitActivity, "Gagal menambah unit", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        dialog.show()
    }

    private fun showOptionsDialog(unit: UnitAlat) {
        val options = arrayOf("Detail Unit", "Hapus Unit")
        AlertDialog.Builder(this)
            .setTitle("Unit: ${unit.kodeInventaris}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> Toast.makeText(this, "Kode: ${unit.kodeInventaris}\nAlat: ${unit.namaAlat}\nStatus: ${unit.statusKetersediaan}", Toast.LENGTH_LONG).show()
                    1 -> deleteUnit(unit.id)
                }
            }
            .show()
    }

    private fun deleteUnit(id: Long) {
        lifecycleScope.launch {
            val success = unitController.deleteUnitAlat(id)
            if (success) {
                Toast.makeText(this@ManageUnitActivity, "Unit dihapus", Toast.LENGTH_SHORT).show()
                loadUnits()
            } else {
                Toast.makeText(this@ManageUnitActivity, "Gagal menghapus unit", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
