package com.pemula.ukk

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.pemula.ukk.adapter.AlatAdapter
import com.pemula.ukk.controller.AlatController
import com.pemula.ukk.databinding.ActivityBerandaBinding
import kotlinx.coroutines.launch

class BerandaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaBinding
    private lateinit var alatAdapter: AlatAdapter
    private val alatController = AlatController()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        alatAdapter = AlatAdapter(emptyList()) { alat ->
            // Add click action if needed
        }
        binding.rvAlat.apply {
            layoutManager = GridLayoutManager(this@BerandaActivity, 2)
            adapter = alatAdapter
        }

        // Fetch Alat from Backend API
        loadAlatData()

        // Top Header Cart / Bag Click
        binding.ivHeaderCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Bottom Nav Profile Click
        binding.ivNavProfile.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadAlatData()
    }

    private fun loadAlatData() {
        lifecycleScope.launch {
            val pagedResult = alatController.fetchAlatList()
            if (pagedResult != null) {
                val list = pagedResult.items
                alatAdapter.updateData(list)

                // Populate Popular Item Banner with the first item
                if (list.isNotEmpty()) {
                    val popularItem = list[0]
                    binding.tvPopularName.text = popularItem.nama
                    val imageUrl = when {
                        popularItem.imagePath.isNullOrEmpty() -> null
                        popularItem.imagePath.startsWith("http") -> popularItem.imagePath
                        else -> "http://192.168.69.58:3001/${popularItem.imagePath.removePrefix("/")}"
                    }

                    if (imageUrl != null) {
                        Glide.with(this@BerandaActivity)
                            .load(imageUrl)
                            .centerCrop()
                            .into(binding.ivPopularImage)
                    }

                    binding.rlPopularBanner.setOnClickListener {
                        val intent = Intent(this@BerandaActivity, FormPeminjamanActivity::class.java).apply {
                            putExtra("EXTRA_ALAT_ID", popularItem.id)
                            putExtra("EXTRA_ALAT_NAME", popularItem.nama)
                            putExtra("EXTRA_ALAT_PRICE", popularItem.hargaSewa)
                            putExtra("EXTRA_ALAT_STOCK", popularItem.stokTersedia)
                            putExtra("EXTRA_ALAT_IMAGE", imageUrl)
                            putExtra("EXTRA_ALAT_KATEGORI_ID", popularItem.idKategori)
                            putExtra("EXTRA_ALAT_KATEGORI", popularItem.kategori)
                            putExtra("EXTRA_ALAT_DESKRIPSI", popularItem.deskripsi)
                        }
                        startActivity(intent)
                    }
                }
            } else {
                Toast.makeText(this@BerandaActivity, "Gagal memuat data alat dari server", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
