package com.pemula.ukk.utils

import com.pemula.ukk.model.AlatResponse

object CartManager {
    data class CartItem(val alat: AlatResponse, var quantity: Int)
    private val itemsMap = mutableMapOf<Long, CartItem>()

    fun addItem(alat: AlatResponse, qty: Int = 1) {
        if (itemsMap.containsKey(alat.id)) {
            itemsMap[alat.id]?.quantity = (itemsMap[alat.id]?.quantity ?: 1) + qty
        } else {
            itemsMap[alat.id] = CartItem(alat, qty)
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
