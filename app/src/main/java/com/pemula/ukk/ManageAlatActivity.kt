package com.pemula.ukk

import android.R
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pemula.ukk.Services.Client
import com.pemula.ukk.adapter.AdminAlatAdapter
import com.pemula.ukk.controller.AlatController
import com.pemula.ukk.databinding.ActivityManageAlatBinding
import com.pemula.ukk.databinding.DialogManageAlatBinding
import com.pemula.ukk.model.AlatResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ManageAlatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageAlatBinding
    private lateinit var adapter: AdminAlatAdapter
    private val alatController = AlatController()
    private var selectedImageUri: Uri? = null
    private var dialogBinding: DialogManageAlatBinding? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            dialogBinding?.ivDialogImagePreview?.clearColorFilter()
            dialogBinding?.ivDialogImagePreview?.setImageURI(uri)
            Toast.makeText(this, "Gambar berhasil dipilih!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageAlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = AdminAlatAdapter(
            emptyList(),
            onEditClick = { item -> showAddEditAlatDialog(item) },
            onDeleteClick = { item -> deleteAlat(item.id) }
        )

        binding.rvAdminAlat.layoutManager = LinearLayoutManager(this)
        binding.rvAdminAlat.adapter = adapter

        binding.btnAddAlat.setOnClickListener {
            showAddEditAlatDialog(null)
        }

        loadAlatData()
    }

    private fun loadAlatData() {
        lifecycleScope.launch {
            val paged = alatController.fetchAlatList(1, 50)
            if (paged != null) {
                adapter.updateData(paged.items)
            } else {
                Toast.makeText(this@ManageAlatActivity, "Gagal memuat data dari server (${Client.BASE_URL})", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAddEditAlatDialog(item: AlatResponse?) {
        selectedImageUri = null
        val dBinding = DialogManageAlatBinding.inflate(layoutInflater)
        dialogBinding = dBinding

        dBinding.tvDialogTitle.text = if (item == null) "Tambah Alat Baru" else "Edit Alat: ${item.nama}"

        if (item != null) {
            dBinding.etDialogNama.setText(item.nama)
            dBinding.etDialogHarga.setText(item.hargaSewa.toString())
            dBinding.etDialogKategori.setText(item.idKategori.toString())
            dBinding.etDialogDeskripsi.setText(item.deskripsi)

            val imageUrl = Client.getImageUrl(item.imagePath)

            if (imageUrl != null) {
                dBinding.ivDialogImagePreview.clearColorFilter()
                Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .into(dBinding.ivDialogImagePreview)
            }
        }

        dBinding.btnDialogPickImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        dBinding.btnDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dBinding.btnDialogSave.setOnClickListener {
            val nama = dBinding.etDialogNama.text.toString().trim()
            val harga = dBinding.etDialogHarga.text.toString().toDoubleOrNull() ?: 0.0
            val idKategori = dBinding.etDialogKategori.text.toString().toLongOrNull() ?: 1L
            val deskripsi = dBinding.etDialogDeskripsi.text.toString().trim()

            if (nama.isNotEmpty() && harga >= 0) {
                saveAlat(item?.id, idKategori, nama, deskripsi, harga)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Nama dan harga sampaikan dengan benar", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun saveAlat(id: Long?, idKategori: Long, nama: String, deskripsi: String?, hargaSewa: Double) {
        lifecycleScope.launch {
            var imagePart: MultipartBody.Part? = null

            selectedImageUri?.let { uri ->
                val file = getFileFromUri(uri)
                if (file != null) {
                    val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
                    val validMimeType = if (mimeType.startsWith("image/")) mimeType else "image/jpeg"
                    val requestFile = file.asRequestBody(validMimeType.toMediaTypeOrNull())
                    val uploadFileName = if (file.name.contains(".")) file.name else "${file.name}.jpg"
                    imagePart = MultipartBody.Part.createFormData("gambar", uploadFileName, requestFile)
                }
            }

            val (success, errorMsg) = if (id == null) {
                alatController.createAlat(idKategori, nama, deskripsi, hargaSewa, imagePart)
            } else {
                alatController.updateAlat(id, idKategori, nama, deskripsi, hargaSewa, imagePart)
            }

            if (success) {
                Toast.makeText(this@ManageAlatActivity, "Data alat berhasil disimpan!", Toast.LENGTH_SHORT).show()
                loadAlatData()
            } else {
                Toast.makeText(this@ManageAlatActivity, errorMsg ?: "Gagal menyimpan data alat", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteAlat(id: Long) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Alat")
            .setMessage("Yakin ingin menghapus alat ini?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    val success = alatController.deleteAlat(id)
                    if (success) {
                        Toast.makeText(this@ManageAlatActivity, "Alat berhasil dihapus", Toast.LENGTH_SHORT).show()
                        loadAlatData()
                    } else {
                        Toast.makeText(this@ManageAlatActivity, "Gagal menghapus alat (terdapat unit/riwayat)", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun getFileFromUri(uri: Uri): File? {
        return try {
            val contentResolver = applicationContext.contentResolver
            val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
            val extension = when {
                mimeType.contains("png", ignoreCase = true) -> ".png"
                mimeType.contains("webp", ignoreCase = true) -> ".webp"
                else -> ".jpg"
            }
            val fileName = "upload_alat_${System.currentTimeMillis()}$extension"
            val tempFile = File(cacheDir, fileName)
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream = FileOutputStream(tempFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
