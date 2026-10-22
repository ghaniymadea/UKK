package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemula.ukk.R
import com.pemula.ukk.model.UnitAlat

class UnitAdapter(
    private var list: List<UnitAlat>,
    private val onItemClick: (UnitAlat) -> Unit
) : RecyclerView.Adapter<UnitAdapter.UnitViewHolder>() {

    class UnitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_generic_title)
        val tvSubtitle: TextView = view.findViewById(R.id.tv_generic_subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_generic, parent, false)
        return UnitViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnitViewHolder, position: Int) {
        val unit = list[position]
        holder.tvTitle.text = "${unit.kodeInventaris} (${unit.namaAlat ?: "Unit"})"
        val barang = unit.statusBarang ?: "baik"
        val ketersediaan = unit.statusKetersediaan ?: "tersedia"
        holder.tvSubtitle.text = "Kondisi: $barang | Status: $ketersediaan"
        holder.itemView.setOnClickListener { onItemClick(unit) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<UnitAlat>) {
        list = newList
        notifyDataSetChanged()
    }
}
