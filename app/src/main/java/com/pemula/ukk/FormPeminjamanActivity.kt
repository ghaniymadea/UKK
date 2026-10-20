package com.pemula.ukk

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.pemula.ukk.controller.PeminjamanController
import com.pemula.ukk.databinding.ActivityFormPeminjamanBinding
import com.pemula.ukk.model.PeminjamanItemRequest
import com.pemula.ukk.model.PeminjamanRequest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class FormPeminjamanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormPeminjamanBinding
    private var quantity = 1
    private var alatId: Long = 1L
    private var alatName: String = "Alat"
    private var alatPrice: Double = 0.0
    private var alatStock: Int = 10
    private var alatImage: String? = null
    private val peminjamanController = PeminjamanController()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityFormPeminjamanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alatId = intent.getLongExtra("EXTRA_ALAT_ID", 1L)
        alatName = intent.getStringExtra("EXTRA_ALAT_NAME") ?: "Alat"
        alatPrice = intent.getDoubleExtra("EXTRA_ALAT_PRICE", 0.0)
        alatStock = intent.getIntExtra("EXTRA_ALAT_STOCK", 10)
        alatImage = intent.getStringExtra("EXTRA_ALAT_IMAGE")

        binding.tvItemName.text = alatName
        binding.tvItemPrice.text = "Rp $alatPrice / hari"
        binding.tvQuantity.text = quantity.toString()
        binding.tvItemStock.text = "Stok tersedia: $alatStock"

        if (!alatImage.isNullOrEmpty()) {
            Glide.with(this).load(alatImage).centerCrop().into(binding.ivFormImage)
        }

        binding.btnPlus.setOnClickListener {
            if (quantity < alatStock) {
                quantity++
                binding.tvQuantity.text = quantity.toString()
            } else {
                Toast.makeText(this, "Stok maksimum tercapai", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }

        // DatePicker for Tanggal Peminjaman
        binding.etTanggalPeminjaman.setOnClickListener {
            showDatePicker { dateStr ->
                binding.etTanggalPeminjaman.setText(dateStr)
            }
        }

        // DatePicker for Tanggal Pengembalian
        binding.etTanggalPengembalian.setOnClickListener {
            showDatePicker { dateStr ->
                binding.etTanggalPengembalian.setText(dateStr)
            }
        }

        binding.btnAddRental.setOnClickListener {
            val tanggalPeminjaman = binding.etTanggalPeminjaman.text.toString().trim()
            val tanggalPengembalian = binding.etTanggalPengembalian.text.toString().trim()

            if (tanggalPeminjaman.isEmpty()) {
                Toast.makeText(this, "Pilih tanggal peminjaman", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tanggalPengembalian.isEmpty()) {
                Toast.makeText(this, "Pilih tanggal pengembalian", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = PeminjamanRequest(
                tanggalPeminjaman = tanggalPeminjaman,
                tanggalPengembalian = tanggalPengembalian,
                items = listOf(
                    PeminjamanItemRequest(
                        idAlat = alatId,
                        jumlah = quantity
                    )
                )
            )

            lifecycleScope.launch {
                val success = peminjamanController.createPeminjaman(request)
                if (success) {
                    Toast.makeText(this@FormPeminjamanActivity, "Pengajuan Peminjaman Berhasil!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@FormPeminjamanActivity, "Gagal mengajukan peminjaman", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format(Locale.US, "%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(formattedDate)
        }, year, month, day).show()
    }
}
