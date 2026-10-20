package com.pemula.ukk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pemula.ukk.R
import com.pemula.ukk.utils.CartManager

class CartAdapter(
    private var cartItems: List<CartManager.CartItem>,
    private val onRemoveClick: (CartManager.CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.iv_cart_image)
        val tvName: TextView = itemView.findViewById(R.id.tv_cart_name)
        val tvPriceQty: TextView = itemView.findViewById(R.id.tv_cart_price_qty)
        val btnRemove: ImageView = itemView.findViewById(R.id.btn_remove_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.tvName.text = item.alat.nama
        holder.tvPriceQty.text = "Rp ${item.alat.hargaSewa} x ${item.quantity}"

        if (!item.alat.imagePath.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.alat.imagePath)
                .centerCrop()
                .into(holder.ivImage)
        } else {
            holder.ivImage.setImageResource(R.drawable.icn)
        }

        holder.btnRemove.setOnClickListener {
            onRemoveClick(item)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateData(newList: List<CartManager.CartItem>) {
        cartItems = newList
        notifyDataSetChanged()
    }
}
