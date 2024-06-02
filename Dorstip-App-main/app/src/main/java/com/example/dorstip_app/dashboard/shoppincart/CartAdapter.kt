package com.example.dorstip_app.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorstip_app.dashboard.shoppincart.CartItem
import com.example.dorstip_app.databinding.ViewholderCartBinding

class CartAdapter(private val items: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = items[position]
        holder.binding.tvCartItemName.text = cartItem.item.title
        holder.binding.tvCartItemPrice.text = "€${cartItem.item.price}"
        holder.binding.tvCartItemQuantity.text = "Quantity: ${cartItem.quantity}"

        Glide.with(holder.itemView.context)
            .load(cartItem.item.picUrl[0])
            .into(holder.binding.ivCartItemImage)
    }

    override fun getItemCount(): Int = items.size
}
