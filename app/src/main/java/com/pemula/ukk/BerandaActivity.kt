package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.pemula.ukk.Services.Client
import com.pemula.ukk.adapter.AlatAdapter
import com.pemula.ukk.controller.AlatController
import com.pemula.ukk.databinding.ActivityBerandaBinding
import com.pemula.ukk.model.AlatResponse
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
            val pagedResult = alatController.fetchAlatList(page = 1, pageSize = 100)
            if (pagedResult != null) {
                val list = pagedResult.items
                alatAdapter.updateData(list)

                // Populate Popular Items Slider with top 3 items
                if (list.isNotEmpty()) {
                    val top3 = list.take(3)

                    if (top3.size >= 1) {
                        val item1 = top3[0]
                        binding.tvPopularName1.text = item1.nama
                        val imgUrl1 = Client.getImageUrl(item1.imagePath)
                        if (imgUrl1 != null) {
                            Glide.with(this@BerandaActivity).load(imgUrl1).centerCrop().into(binding.ivPopularImage1)
                        }
                        binding.rlPopularBanner1.setOnClickListener { openFormPeminjaman(item1, imgUrl1) }
                    }

                    if (top3.size >= 2) {
                        val item2 = top3[1]
                        binding.tvPopularName2.text = item2.nama
                        val imgUrl2 = Client.getImageUrl(item2.imagePath)
                        if (imgUrl2 != null) {
                            Glide.with(this@BerandaActivity).load(imgUrl2).centerCrop().into(binding.ivPopularImage2)
                        }
                        binding.rlPopularBanner2.setOnClickListener { openFormPeminjaman(item2, imgUrl2) }
                    }

                    if (top3.size >= 3) {
                        val item3 = top3[2]
                        binding.tvPopularName3.text = item3.nama
                        val imgUrl3 = Client.getImageUrl(item3.imagePath)
                        if (imgUrl3 != null) {
                            Glide.with(this@BerandaActivity).load(imgUrl3).centerCrop().into(binding.ivPopularImage3)
                        }
                        binding.rlPopularBanner3.setOnClickListener { openFormPeminjaman(item3, imgUrl3) }
                    }

                    binding.vfPopularSlider.flipInterval = 3000
                    binding.vfPopularSlider.startFlipping()
                }
            } else {
                Toast.makeText(this@BerandaActivity, "Gagal memuat data alat dari server", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openFormPeminjaman(item: AlatResponse, imageUrl: String?) {
        val intent = Intent(this@BerandaActivity, FormPeminjamanActivity::class.java).apply {
            putExtra("EXTRA_ALAT_ID", item.id)
            putExtra("EXTRA_ALAT_NAME", item.nama)
            putExtra("EXTRA_ALAT_PRICE", item.hargaSewa)
            putExtra("EXTRA_ALAT_STOCK", item.stokTersedia)
            putExtra("EXTRA_ALAT_IMAGE", imageUrl)
            putExtra("EXTRA_ALAT_KATEGORI_ID", item.idKategori)
            putExtra("EXTRA_ALAT_KATEGORI", item.kategori)
            putExtra("EXTRA_ALAT_DESKRIPSI", item.deskripsi)
        }
        startActivity(intent)
    }
}
