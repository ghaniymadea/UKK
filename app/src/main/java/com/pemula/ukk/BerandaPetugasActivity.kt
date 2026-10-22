package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pemula.ukk.controller.PeminjamanController
import com.pemula.ukk.databinding.ActivityBerandaPetugasBinding
import com.pemula.ukk.utils.SessionManager
import kotlinx.coroutines.launch

class BerandaPetugasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaPetugasBinding
    private lateinit var sessionManager: SessionManager
    private val peminjamanController = PeminjamanController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaPetugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        loadPetugasStats()

        // Profile Nav Click
        binding.ivNavProfile.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        val openApproval = { startActivity(Intent(this, PetugasApprovalActivity::class.java)) }
        val openMonitor = { startActivity(Intent(this, PetugasMonitorActivity::class.java)) }
        val openLaporan = { startActivity(Intent(this, PetugasLaporanActivity::class.java)) }

        binding.cardApprovalPeminjaman.setOnClickListener { openApproval() }
        binding.gridApproval.setOnClickListener { openApproval() }

        binding.cardMonitorPeminjaman.setOnClickListener { openMonitor() }
        binding.gridMonitor.setOnClickListener { openMonitor() }

        binding.gridLaporan.setOnClickListener { openLaporan() }
    }

    private fun loadPetugasStats() {
        lifecycleScope.launch {
            val pending = peminjamanController.fetchPeminjamanList(status = "diajukan")
            binding.tvCountApproval.text = (pending?.total ?: 0).toString()

            val monitor = peminjamanController.fetchMonitor()
            binding.tvCountMonitor.text = (monitor?.total ?: 0).toString()
        }
    }
}
