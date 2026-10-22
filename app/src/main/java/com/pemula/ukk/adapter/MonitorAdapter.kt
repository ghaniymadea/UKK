package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemula.ukk.R
import com.pemula.ukk.model.MonitorPeminjaman

class MonitorAdapter(
    private var list: List<MonitorPeminjaman>,
    private val onItemClick: (MonitorPeminjaman) -> Unit
) : RecyclerView.Adapter<MonitorAdapter.MonitorViewHolder>() {

    class MonitorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUser: TextView = view.findViewById(R.id.tv_monitor_user)
        val tvLate: TextView = view.findViewById(R.id.tv_monitor_late)
        val tvPhone: TextView = view.findViewById(R.id.tv_monitor_phone)
        val tvDates: TextView = view.findViewById(R.id.tv_monitor_dates)
        val btnProcess: Button = view.findViewById(R.id.btn_process_return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonitorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_monitor, parent, false)
        return MonitorViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonitorViewHolder, position: Int) {
        val item = list[position]
        holder.tvUser.text = item.peminjam ?: "Peminjam"
        holder.tvPhone.text = "No Telp: ${item.noTelepon ?: "-"}"
        holder.tvDates.text = "Tgl Kembali: ${item.tanggalPengembalian}"

        if (item.hariTerlambat > 0) {
            holder.tvLate.text = "TELAT ${item.hariTerlambat} HARI"
            holder.tvLate.setBackgroundResource(R.drawable.bg_btn_logout)
        } else {
            holder.tvLate.text = "TEPAT WAKTU"
            holder.tvLate.setBackgroundResource(R.drawable.bg_btn_login)
        }

        holder.btnProcess.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<MonitorPeminjaman>) {
        list = newList
        notifyDataSetChanged()
    }
}
