package com.pemula.ukk

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
    private var isPasswordVisible = false
    private lateinit var sessionManager: SessionManager
    private val authController = AuthController()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Toggle password visibility
        binding.ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.ivTogglePassword.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.btnLogin.setOnClickListener {
            val nameEmail = binding.etNameEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (nameEmail.isEmpty()) {
                binding.etNameEmail.error = "Name/Phone/Email tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val (result, errorMsg) = authController.login(nameEmail, password)
                if (result != null) {
                    sessionManager.saveLoginSession(
                        token = result.token,
                        userId = result.user.id,
                        name = result.user.nama,
                        role = result.user.role,
                        phone = nameEmail,
                        alamat = "Alamat belum diatur"
                    )
                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, BerandaActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, errorMsg ?: "Login Gagal", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
