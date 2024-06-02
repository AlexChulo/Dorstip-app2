package com.example.dorstip_app.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dorstip_app.dashboard.shoppingcart.ShoppingCart
import com.example.dorstip_app.databinding.ActivityCartBinding

// Activity for displaying the shopping cart
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding // View binding for activity_cart.xml
    private lateinit var adapter: CartAdapter // RecyclerView adapter for cart items

    // Called when the activity is starting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCart() // Initialize the shopping cart
    }

    // Initialize the shopping cart
    private fun initCart() {
        val cartItems = ShoppingCart.getItems() // Get cart items from ShoppingCart singleton
        adapter = CartAdapter(cartItems) // Create adapter with cart items
        binding.rvCartItems.layoutManager = LinearLayoutManager(this) // Set layout manager for RecyclerView
        binding.rvCartItems.adapter = adapter // Set adapter for RecyclerView

        binding.tvTotalPrice.text = "Total: €${ShoppingCart.getTotalPrice()}" // Display total price of items in the cart

        binding.ibBack.setOnClickListener { finish() } // Handle back button click
    }
}
