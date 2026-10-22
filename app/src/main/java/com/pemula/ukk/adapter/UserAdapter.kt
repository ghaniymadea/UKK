package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemula.ukk.R
import com.pemula.ukk.model.UserResponse

class UserAdapter(
    private var list: List<UserResponse>,
    private val onItemClick: (UserResponse) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_generic_title)
        val tvSubtitle: TextView = view.findViewById(R.id.tv_generic_subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_generic, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.tvTitle.text = user.nama
        val role = user.role ?: "Peminjam"
        val phone = user.noTelepon ?: "-"
        holder.tvSubtitle.text = "Role: $role | Telp: $phone"
        holder.itemView.setOnClickListener { onItemClick(user) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<UserResponse>) {
        list = newList
        notifyDataSetChanged()
    }
}
