package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemula.ukk.R
import com.pemula.ukk.model.PeminjamanResponse

class ApprovalAdapter(
    private var list: List<PeminjamanResponse>,
    private val onApproveClick: (PeminjamanResponse) -> Unit,
    private val onRejectClick: (PeminjamanResponse) -> Unit,
    private val onBorrowClick: (PeminjamanResponse) -> Unit
) : RecyclerView.Adapter<ApprovalAdapter.ApprovalViewHolder>() {

    class ApprovalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUser: TextView = view.findViewById(R.id.tv_approval_user)
        val tvStatus: TextView = view.findViewById(R.id.tv_approval_status)
        val tvDates: TextView = view.findViewById(R.id.tv_approval_dates)
        val tvPrice: TextView = view.findViewById(R.id.tv_approval_price)
        val btnApprove: Button = view.findViewById(R.id.btn_approve)
        val btnReject: Button = view.findViewById(R.id.btn_reject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApprovalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_approval, parent, false)
        return ApprovalViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApprovalViewHolder, position: Int) {
        val item = list[position]
        holder.tvUser.text = "Peminjam: ${item.peminjam ?: "User #${item.idPeminjam}"}"
        holder.tvStatus.text = item.status ?: "diajukan"
        holder.tvDates.text = "Tgl: ${item.tanggalPeminjaman} s/d ${item.tanggalPengembalian}"
        holder.tvPrice.text = "Total Biaya: Rp ${item.totalBiaya}"

        when (item.status?.lowercase()) {
            "diajukan" -> {
                holder.btnApprove.text = "Setujui"
                holder.btnApprove.visibility = View.VISIBLE
                holder.btnApprove.setOnClickListener { onApproveClick(item) }

                holder.btnReject.text = "Tolak"
                holder.btnReject.visibility = View.VISIBLE
                holder.btnReject.setOnClickListener { onRejectClick(item) }
            }
            "disetujui" -> {
                holder.btnApprove.text = "Serah Terima (Borrow)"
                holder.btnApprove.visibility = View.VISIBLE
                holder.btnApprove.setOnClickListener { onBorrowClick(item) }

                holder.btnReject.visibility = View.GONE
            }
            else -> {
                holder.btnApprove.visibility = View.GONE
                holder.btnReject.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<PeminjamanResponse>) {
        list = newList
        notifyDataSetChanged()
    }
}
