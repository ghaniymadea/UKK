package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.pemula.ukk.controller.AuthController
import com.pemula.ukk.databinding.ActivityLoginBinding
import com.pemula.ukk.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private val authController = AuthController()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Login Button Click
        binding.btnLogin.setOnClickListener {
            val nameEmail = binding.etNameEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            binding.tilNameEmail.error = null
            binding.tilPassword.error = null

            if (nameEmail.isEmpty()) {
                binding.tilNameEmail.error = "Nomor telepon/Username tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                binding.btnLogin.isEnabled = false

                try {
                    val (result, errorMsg) = authController.login(nameEmail, password)

                    if (result != null) {
                        Log.d("LOGIN_DEBUG", "Alamat dari API: ${result.user.alamat}")

                        // Simpan data session pengguna
                        sessionManager.saveLoginSession(
                            token = result.token,
                            userId = result.user.id,
                            name = result.user.nama,
                            role = result.user.role ?: "",
                            phone = result.user.noTelepon ?: nameEmail,
                            alamat = result.user.alamat
                        )

                        Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()

                        val role = result.user.role ?: ""
                        val intent = when (role.lowercase()) {
                            "admin" -> Intent(this@LoginActivity, BerandaAdminActivity::class.java)
                            "petugas" -> Intent(this@LoginActivity, BerandaPetugasActivity::class.java)
                            else -> Intent(this@LoginActivity, BerandaActivity::class.java)
                        }
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            errorMsg ?: "Login Gagal",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.e("LOGIN_DEBUG", "Terjadi kesalahan saat login", e)
                    Toast.makeText(
                        this@LoginActivity,
                        "Terjadi kesalahan saat login",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    binding.btnLogin.isEnabled = true
                }
            }
        }
    }
}
