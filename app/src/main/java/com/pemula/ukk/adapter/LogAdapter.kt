package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemula.ukk.R
import com.pemula.ukk.model.LogAktivitas

class LogAdapter(
    private var list: List<LogAktivitas>
) : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    class LogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_generic_title)
        val tvSubtitle: TextView = view.findViewById(R.id.tv_generic_subtitle)
        val ivArrow: View = view.findViewById(R.id.iv_generic_arrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_generic, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = list[position]
        holder.tvTitle.text = log.aktivitas
        val user = log.namaUser ?: "User #${log.idUser}"
        val time = log.waktu ?: "-"
        holder.tvSubtitle.text = "Oleh: $user | Waktu: $time"
        holder.ivArrow.visibility = View.GONE
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<LogAktivitas>) {
        list = newList
        notifyDataSetChanged()
    }
}
