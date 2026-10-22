package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pemula.ukk.controller.*
import com.pemula.ukk.databinding.ActivityBerandaAdminBinding
import com.pemula.ukk.utils.SessionManager
import kotlinx.coroutines.launch

class BerandaAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaAdminBinding
    private lateinit var sessionManager: SessionManager
    private val alatController = AlatController()
    private val usersController = UsersController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        loadAdminStats()

        // Profile Nav Click
        binding.ivNavProfile.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        val openAlat = { startActivity(Intent(this, ManageAlatActivity::class.java)) }
        binding.cardManageAlat.setOnClickListener { openAlat() }
        binding.gridManageAlat.setOnClickListener { openAlat() }

        val openUsers = { startActivity(Intent(this, ManageUsersActivity::class.java)) }
        binding.cardManageUsers.setOnClickListener { openUsers() }
        binding.gridManageUsers.setOnClickListener { openUsers() }

        binding.cardManageKategori.setOnClickListener {
            startActivity(Intent(this, ManageKategoriActivity::class.java))
        }

        binding.cardManageUnit.setOnClickListener {
            startActivity(Intent(this, ManageUnitActivity::class.java))
        }

        binding.cardViewLogs.setOnClickListener {
            startActivity(Intent(this, ManageLogsActivity::class.java))
        }
    }

    private fun loadAdminStats() {
        lifecycleScope.launch {
            val alat = alatController.fetchAlatList(1, 1)
            binding.tvCountAlat.text = (alat?.total ?: 0).toString()

            val users = usersController.fetchUsersList(1, 1)
            binding.tvCountUsers.text = (users?.total ?: 0).toString()
        }
    }
}
