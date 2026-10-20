package com.pemula.ukk

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.pemula.ukk.databinding.ActivityProfilBinding
import com.pemula.ukk.utils.SessionManager

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Populate Profile with Login Data
        binding.tvProfileName.text = sessionManager.getUserName() ?: "name"
        binding.tvProfileAlamat.text = sessionManager.getUserAlamat() ?: "alamat"
        binding.tvProfileTelp.text = sessionManager.getUserPhone() ?: "nomor telp"

        // Bottom Nav Home Click
        binding.ivNavHome.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        // LogOut Button Click
        binding.btnLogout.setOnClickListener {
            sessionManager.clearSession()
            Toast.makeText(this, "Berhasil LogOut", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
