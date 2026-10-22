package com.pemula.ukk.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pemula.ukk.FormPeminjamanActivity
import com.pemula.ukk.R
import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.AlatResponse
import com.pemula.ukk.utils.CartManager

class AlatAdapter(
    private var alatList: List<AlatResponse>,
    private val onAddClick: (AlatResponse) -> Unit
) : RecyclerView.Adapter<AlatAdapter.AlatViewHolder>() {

    class AlatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.iv_alat_image)
        val tvName: TextView = itemView.findViewById(R.id.tv_alat_name)
        val tvStock: TextView = itemView.findViewById(R.id.tv_alat_stock)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_alat_price)
        val btnAdd: ImageView = itemView.findViewById(R.id.btn_add_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alat, parent, false)
        return AlatViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlatViewHolder, position: Int) {
        val alat = alatList[position]
        holder.tvName.text = alat.nama
        holder.tvStock.text = "Stok: ${alat.stokTersedia}"
        holder.tvPrice.text = "Rp ${alat.hargaSewa} / hari"

        val imageUrl = Client.getImageUrl(alat.imagePath)

        if (imageUrl != null) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.icn)
                .error(R.drawable.icn)
                .into(holder.ivImage)
        } else {
            holder.ivImage.setImageResource(R.drawable.icn)
        }

        if (alat.stokTersedia <= 0) {
            holder.btnAdd.alpha = 0.5f
            holder.btnAdd.isEnabled = false
        } else {
            holder.btnAdd.alpha = 1.0f
            holder.btnAdd.isEnabled = true
        }

        holder.btnAdd.setOnClickListener {
            CartManager.addItem(alat, 1)
            Toast.makeText(holder.itemView.context, "${alat.nama} dimasukkan ke tas rental", Toast.LENGTH_SHORT).show()
            onAddClick(alat)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FormPeminjamanActivity::class.java).apply {
                putExtra("EXTRA_ALAT_ID", alat.id)
                putExtra("EXTRA_ALAT_NAME", alat.nama)
                putExtra("EXTRA_ALAT_PRICE", alat.hargaSewa)
                putExtra("EXTRA_ALAT_STOCK", alat.stokTersedia)
                putExtra("EXTRA_ALAT_IMAGE", imageUrl)
                putExtra("EXTRA_ALAT_KATEGORI_ID", alat.idKategori)
                putExtra("EXTRA_ALAT_KATEGORI", alat.kategori)
                putExtra("EXTRA_ALAT_DESKRIPSI", alat.deskripsi)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = alatList.size

    fun updateData(newList: List<AlatResponse>) {
        alatList = newList
        notifyDataSetChanged()
    }
}
