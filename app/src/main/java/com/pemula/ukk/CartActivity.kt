package com.pemula.ukk

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemula.ukk.adapter.CartAdapter
import com.pemula.ukk.controller.PeminjamanController
import com.pemula.ukk.databinding.ActivityCartBinding
import com.pemula.ukk.model.PeminjamanItemRequest
import com.pemula.ukk.model.PeminjamanRequest
import com.pemula.ukk.utils.CartManager
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val peminjamanController = PeminjamanController()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        cartAdapter = CartAdapter(
            cartItems = CartManager.getItems(),
            onQuantityChanged = {
                // Quantity updated
            },
            onRemoveClick = { item ->
                CartManager.removeItem(item.alat.id)
                cartAdapter.updateData(CartManager.getItems())
                Toast.makeText(this, "${item.alat.nama} dihapus dari tas", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }

        binding.etCartTanggalPeminjaman.setOnClickListener {
            showDatePicker { dateStr ->
                binding.etCartTanggalPeminjaman.setText(dateStr)
            }
        }

        binding.etCartTanggalPengembalian.setOnClickListener {
            showDatePicker { dateStr ->
                binding.etCartTanggalPengembalian.setText(dateStr)
            }
        }

        binding.btnPinjamCart.setOnClickListener {
            val items = CartManager.getItems()
            if (items.isEmpty()) {
                Toast.makeText(this, "Tas rental masih kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tanggalPeminjaman = binding.etCartTanggalPeminjaman.text.toString().trim()
            val tanggalPengembalian = binding.etCartTanggalPengembalian.text.toString().trim()

            if (tanggalPeminjaman.isEmpty() || tanggalPengembalian.isEmpty()) {
                Toast.makeText(this, "Pilih tanggal peminjaman dan pengembalian", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loanItems = items.map {
                PeminjamanItemRequest(
                    idAlat = it.alat.id,
                    jumlah = it.quantity
                )
            }

            val request = PeminjamanRequest(
                tanggalPeminjaman = tanggalPeminjaman,
                tanggalPengembalian = tanggalPengembalian,
                items = loanItems
            )

            lifecycleScope.launch {
                val success = peminjamanController.createPeminjaman(request)
                if (success) {
                    Toast.makeText(this@CartActivity, "Peminjaman Berhasil Diajukan!", Toast.LENGTH_LONG).show()
                    CartManager.clear()
                    finish()
                } else {
                    Toast.makeText(this@CartActivity, "Gagal mengajukan peminjaman", Toast.LENGTH_SHORT).show()
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
