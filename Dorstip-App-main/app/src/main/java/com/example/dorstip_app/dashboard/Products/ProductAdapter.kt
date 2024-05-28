package com.example.dorstip_app.dashboard.Products

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.dorstip_app.dashboard.DetailActivity
import com.example.dorstip_app.databinding.ViewholderRecommendedBinding

class ProductAdapter(val items: MutableList<ItemModel>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // ViewHolder class to hold the views
    class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var context: Context? = null

    // onCreateViewHolder method to inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    // onBindViewHolder method to bind data to each item's views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to views for each item
        holder.binding.tvDrinkName.text = items[position].title
        holder.binding.tvPrice.text = "€" + items[position].price.toString()
        holder.binding.tvRating.text = items[position].rating.toString()

        // Load image using Glide library with CenterCrop transformation
        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .fitCenter()
            .into(holder.binding.ivDrink)

        // Set click listener for each item to open DetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    // getItemCount method to return the number of items in the list
    override fun getItemCount(): Int = items.size
}