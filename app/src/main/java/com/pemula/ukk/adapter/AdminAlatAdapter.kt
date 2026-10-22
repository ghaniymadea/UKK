package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pemula.ukk.R
import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.AlatResponse

class AdminAlatAdapter(
    private var list: List<AlatResponse>,
    private val onEditClick: (AlatResponse) -> Unit,
    private val onDeleteClick: (AlatResponse) -> Unit
) : RecyclerView.Adapter<AdminAlatAdapter.AdminAlatViewHolder>() {

    class AdminAlatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.iv_alat_image)
        val tvName: TextView = view.findViewById(R.id.tv_alat_name)
        val tvKategori: TextView = view.findViewById(R.id.tv_alat_kategori)
        val tvPriceStock: TextView = view.findViewById(R.id.tv_alat_price_stock)
        val btnEdit: ImageView = view.findViewById(R.id.btn_edit_alat)
        val btnDelete: ImageView = view.findViewById(R.id.btn_delete_alat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminAlatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_alat, parent, false)
        return AdminAlatViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminAlatViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.nama
        holder.tvKategori.text = "Kategori: ${item.kategori ?: "-"}"
        holder.tvPriceStock.text = "Rp ${item.hargaSewa} / hari | Stok: ${item.stokTersedia}"

        val imageUrl = Client.getImageUrl(item.imagePath)

        if (imageUrl != null) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivImage)
        } else {
            holder.ivImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnDelete.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<AlatResponse>) {
        list = newList
        notifyDataSetChanged()
    }
}
