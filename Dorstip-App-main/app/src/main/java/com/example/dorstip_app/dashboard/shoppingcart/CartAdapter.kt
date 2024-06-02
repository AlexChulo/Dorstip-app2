package com.example.dorstip_app.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorstip_app.dashboard.shoppingcart.CartItem
import com.example.dorstip_app.databinding.ViewholderCartBinding

// Adapter for displaying cart items in RecyclerView
class CartAdapter(private val items: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    // ViewHolder class to hold the views
    class ViewHolder(val binding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Bind data to ViewHolder views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = items[position]
        holder.binding.tvCartItemName.text = cartItem.item.title // Set item name
        holder.binding.tvCartItemPrice.text = "€${cartItem.item.price}" // Set item price
        holder.binding.tvCartItemQuantity.text = "Quantity: ${cartItem.quantity}" // Set item quantity

        // Load item image using Glide library
        Glide.with(holder.itemView.context)
            .load(cartItem.item.picUrl[0]) // Load first image URL
            .into(holder.binding.ivCartItemImage) // Set image into ImageView
    }

    // Return the number of items in the list
    override fun getItemCount(): Int = items.size
}
