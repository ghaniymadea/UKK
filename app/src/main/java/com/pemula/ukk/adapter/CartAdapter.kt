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
    private val onQuantityChanged: () -> Unit,
    private val onRemoveClick: (CartManager.CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.iv_cart_image)
        val tvName: TextView = itemView.findViewById(R.id.tv_cart_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_cart_price)
        val tvQuantity: TextView = itemView.findViewById(R.id.tv_cart_quantity)
        val btnMinus: TextView = itemView.findViewById(R.id.btn_cart_minus)
        val btnPlus: TextView = itemView.findViewById(R.id.btn_cart_plus)
        val btnRemove: ImageView = itemView.findViewById(R.id.btn_remove_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.tvName.text = item.alat.nama
        holder.tvPrice.text = "Rp ${item.alat.hargaSewa} / hari"
        holder.tvQuantity.text = item.quantity.toString()

        val imageUrl = when {
            item.alat.imagePath.isNullOrEmpty() -> null
            item.alat.imagePath.startsWith("http") -> item.alat.imagePath
            else -> "http://192.168.69.58:3001/${item.alat.imagePath.removePrefix("/")}"
        }

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

        holder.btnPlus.setOnClickListener {
            if (item.quantity < item.alat.stokTersedia) {
                CartManager.updateQuantity(item.alat.id, item.quantity + 1)
                updateData(CartManager.getItems())
                onQuantityChanged()
            }
        }

        holder.btnMinus.setOnClickListener {
            if (item.quantity > 1) {
                CartManager.updateQuantity(item.alat.id, item.quantity - 1)
                updateData(CartManager.getItems())
                onQuantityChanged()
            }
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
