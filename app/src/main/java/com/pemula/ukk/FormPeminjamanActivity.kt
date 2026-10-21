package com.pemula.ukk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.pemula.ukk.databinding.ActivityFormPeminjamanBinding
import com.pemula.ukk.model.AlatResponse
import com.pemula.ukk.utils.CartManager

class FormPeminjamanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormPeminjamanBinding
    private var quantity = 1
    private var alatId: Long = 1L
    private var alatName: String = "Alat"
    private var alatPrice: Double = 0.0
    private var alatStock: Int = 10
    private var alatImage: String? = null
    private var alatKategoriId: Long = 0L
    private var alatKategori: String? = null
    private var alatDeskripsi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityFormPeminjamanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        alatId = intent.getLongExtra("EXTRA_ALAT_ID", 1L)
        alatName = intent.getStringExtra("EXTRA_ALAT_NAME") ?: "Alat"
        alatPrice = intent.getDoubleExtra("EXTRA_ALAT_PRICE", 0.0)
        alatStock = intent.getIntExtra("EXTRA_ALAT_STOCK", 10)
        alatImage = intent.getStringExtra("EXTRA_ALAT_IMAGE")
        alatKategoriId = intent.getLongExtra("EXTRA_ALAT_KATEGORI_ID", 0L)
        alatKategori = intent.getStringExtra("EXTRA_ALAT_KATEGORI")
        alatDeskripsi = intent.getStringExtra("EXTRA_ALAT_DESKRIPSI")

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

        binding.btnAddRental.setOnClickListener {
            val alat = AlatResponse(
                id = alatId,
                idKategori = alatKategoriId,
                kategori = alatKategori,
                nama = alatName,
                deskripsi = alatDeskripsi,
                hargaSewa = alatPrice,
                imagePath = alatImage,
                stokTersedia = alatStock,
                createdAt = null
            )
            CartManager.addItem(alat, quantity)
            Toast.makeText(this, "$alatName ($quantity) dimasukkan ke tas", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
