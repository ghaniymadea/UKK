package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemula.ukk.R
import com.pemula.ukk.model.Kategori

class KategoriAdapter(
    private var list: List<Kategori>,
    private val onItemClick: (Kategori) -> Unit
) : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

    class KategoriViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_generic_title)
        val tvSubtitle: TextView = view.findViewById(R.id.tv_generic_subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_generic, parent, false)
        return KategoriViewHolder(view)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val item = list[position]
        holder.tvTitle.text = item.nama
        holder.tvSubtitle.text = "ID Kategori: #${item.id}"
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Kategori>) {
        list = newList
        notifyDataSetChanged()
    }
}
