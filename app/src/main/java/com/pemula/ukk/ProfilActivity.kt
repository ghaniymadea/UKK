package com.pemula.ukk

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.pemula.ukk.controller.AuthController
import com.pemula.ukk.databinding.ActivityProfilBinding
import com.pemula.ukk.utils.SessionManager
import kotlinx.coroutines.launch

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding
    private lateinit var sessionManager: SessionManager
    private val authController = AuthController()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Populate Profile with Session Data from Login
        updateProfileUI()

        // Fetch fresh User Profile from API (GET api/v1/auth/me)
        lifecycleScope.launch {
            val user = authController.getProfile()
            if (user != null) {
                sessionManager.saveLoginSession(
                    token = sessionManager.getToken(),
                    userId = user.id,
                    name = user.nama,
                    role = user.role ?: sessionManager.getUserRole(),
                    phone = user.noTelepon ?: sessionManager.getUserPhone(),
                    alamat = user.alamat
                )
                updateProfileUI()
            }
        }

        // Bottom Nav Home Click
        binding.ivNavHome.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        // LogOut Button Click with Confirmation Dialog
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Keluar")
                .setMessage("Yakin mau keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    sessionManager.clearSession()
                    Toast.makeText(this, "Berhasil LogOut", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    private fun updateProfileUI() {
        val name = sessionManager.getUserName()
        val alamat = sessionManager.getUserAlamat()
        val phone = sessionManager.getUserPhone()

        binding.tvProfileName.text = if (!name.isNullOrEmpty() && name != "name") name else "Peminjam CampGear"
        binding.tvProfileAlamat.text = if (!alamat.isNullOrEmpty() && alamat != "alamat" && alamat != "-") alamat else "Alamat belum diatur"
        binding.tvProfileTelp.text = if (!phone.isNullOrEmpty() && phone != "nomor telp") phone else "-"
    }
}
