package com.example.dorstip_app.dashboard.shoppingcart

import com.example.dorstip_app.dashboard.Products.ItemModel

object ShoppingCart {
    private val items = mutableListOf<CartItem>()

    fun addItem(item: ItemModel, quantity: Int) {
        val cartItem = items.find { it.item == item }
        if (cartItem != null) {
            cartItem.quantity += quantity
        } else {
            items.add(CartItem(item, quantity))
        }
    }

    fun getItems(): List<CartItem> {
        return items
    }

    fun getTotalPrice(): Double {
        return items.sumOf { it.item.price * it.quantity }
    }
}