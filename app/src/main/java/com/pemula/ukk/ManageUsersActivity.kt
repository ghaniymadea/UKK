package com.pemula.ukk

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemula.ukk.adapter.UserAdapter
import com.pemula.ukk.controller.UsersController
import com.pemula.ukk.databinding.ActivityManageUsersBinding
import com.pemula.ukk.databinding.DialogManageUserBinding
import com.pemula.ukk.model.UserRequest
import com.pemula.ukk.model.UserResponse
import kotlinx.coroutines.launch

class ManageUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageUsersBinding
    private lateinit var adapter: UserAdapter
    private val usersController = UsersController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = UserAdapter(emptyList()) { user ->
            showOptionsDialog(user)
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        binding.btnAddUser.setOnClickListener {
            showAddUserDialog()
        }

        loadUsers()
    }

    private fun loadUsers() {
        lifecycleScope.launch {
            val paged = usersController.fetchUsersList(1, 50)
            if (paged != null) {
                adapter.updateData(paged.items)
            }
        }
    }

    private fun showAddUserDialog() {
        val dBinding = DialogManageUserBinding.inflate(layoutInflater)
        dBinding.tvDialogTitle.text = "Tambah User Baru"

        val dialog = AlertDialog.Builder(this)
            .setView(dBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        dBinding.btnDialogCancel.setOnClickListener { dialog.dismiss() }
        dBinding.btnDialogSave.setOnClickListener {
            val name = dBinding.etDialogUserNama.text.toString().trim()
            val phone = dBinding.etDialogUserPhone.text.toString().trim()
            val pass = dBinding.etDialogUserPassword.text.toString().trim()
            val alamat = dBinding.etDialogUserAlamat.text.toString().trim()

            if (name.isNotEmpty() && phone.isNotEmpty() && pass.isNotEmpty()) {
                lifecycleScope.launch {
                    val req = UserRequest(
                        idRole = 3, // Peminjam by default
                        idJenisKelamin = 1,
                        nama = name,
                        password = pass,
                        noTelepon = phone,
                        alamat = alamat
                    )
                    val success = usersController.createUser(req)
                    if (success) {
                        Toast.makeText(this@ManageUsersActivity, "User berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        loadUsers()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this@ManageUsersActivity, "Gagal menambah user", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        dialog.show()
    }

    private fun showOptionsDialog(user: UserResponse) {
        val options = arrayOf("Detail / Edit", "Hapus User")
        AlertDialog.Builder(this)
            .setTitle("Opsi User: ${user.nama}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> Toast.makeText(this, "Nama: ${user.nama}\nPhone: ${user.noTelepon}\nRole: ${user.role}", Toast.LENGTH_LONG).show()
                    1 -> deleteUser(user.id)
                }
            }
            .show()
    }

    private fun deleteUser(id: Long) {
        lifecycleScope.launch {
            val success = usersController.deleteUser(id)
            if (success) {
                Toast.makeText(this@ManageUsersActivity, "User dihapus", Toast.LENGTH_SHORT).show()
                loadUsers()
            } else {
                Toast.makeText(this@ManageUsersActivity, "Gagal menghapus user (terdapat riwayat transaksi)", Toast.LENGTH_LONG).show()
            }
        }
    }
}
