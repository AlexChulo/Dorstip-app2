package com.example.dorstip_app.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dorstip_app.dashboard.shoppingcart.ShoppingCart
import com.example.dorstip_app.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding // View binding
    private lateinit var adapter: CartAdapter // RecyclerView adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCart()
    }

    private fun initCart() {
        val cartItems = ShoppingCart.getItems()
        adapter = CartAdapter(cartItems)
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)
        binding.rvCartItems.adapter = adapter

        binding.tvTotalPrice.text = "Total: €${ShoppingCart.getTotalPrice()}"

        binding.ibBack.setOnClickListener { finish() }
    }
}
