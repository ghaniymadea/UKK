package com.pemula.ukk.utils

import com.pemula.ukk.model.AlatResponse

object CartManager {
    data class CartItem(val alat: AlatResponse, var quantity: Int)
    private val itemsMap = mutableMapOf<Long, CartItem>()

    fun addItem(alat: AlatResponse, qty: Int = 1) {
        if (itemsMap.containsKey(alat.id)) {
            val currentQty = itemsMap[alat.id]?.quantity ?: 1
            val newQty = currentQty + qty
            if (newQty <= alat.stokTersedia) {
                itemsMap[alat.id]?.quantity = newQty
            } else {
                itemsMap[alat.id]?.quantity = alat.stokTersedia
            }
        } else {
            val initialQty = if (qty <= alat.stokTersedia) qty else alat.stokTersedia
            itemsMap[alat.id] = CartItem(alat, initialQty)
        }
    }

    fun updateQuantity(alatId: Long, newQty: Int) {
        if (newQty <= 0) {
            itemsMap.remove(alatId)
        } else {
            itemsMap[alatId]?.let { item ->
                if (newQty <= item.alat.stokTersedia) {
                    item.quantity = newQty
                }
            }
        }
    }

    fun removeItem(alatId: Long) {
        itemsMap.remove(alatId)
    }

    fun getItems(): List<CartItem> {
        return itemsMap.values.toList()
    }

    fun clear() {
        itemsMap.clear()
    }

    fun getTotalItemsCount(): Int {
        return itemsMap.values.sumOf { it.quantity }
    }
}
