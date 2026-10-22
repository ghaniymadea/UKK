package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.pemula.ukk.utils.SessionManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sessionManager = SessionManager(this)
        val token = sessionManager.getToken()
        val role = sessionManager.getUserRole() ?: ""

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (!token.isNullOrEmpty()) {
                when (role.lowercase()) {
                    "admin" -> Intent(this, BerandaAdminActivity::class.java)
                    "petugas" -> Intent(this, BerandaPetugasActivity::class.java)
                    else -> Intent(this, BerandaActivity::class.java)
                }
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 1500)
    }
}
