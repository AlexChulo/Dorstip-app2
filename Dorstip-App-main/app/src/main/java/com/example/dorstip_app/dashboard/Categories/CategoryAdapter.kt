package com.example.dorstip_app.dashboard.Categories

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorstip_app.R
import com.example.dorstip_app.dashboard.Products.ProductListActivity
import com.example.dorstip_app.databinding.ViewholderCategoryBinding

class CategoryAdapter(private val items: MutableList<CategoryModel>, private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    // ViewHolder class to hold the views
    class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    // onCreateViewHolder method to inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // onBindViewHolder method to bind data to each item's views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvCategory.text = item.title

        // Load image using Glide library
        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.ivCategory)

        // Click listener for each item
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, ProductListActivity::class.java).apply {
                putExtra("CATEGORY_TITLE", item.title)
            }
            context.startActivity(intent)
        }

        // Change background and visibility based on item selection
        if (selectedPosition == position) {
            holder.binding.ivCategory.setBackgroundColor(0)
            holder.binding.layoutCategory.setBackgroundResource(R.drawable.category_background2)
            holder.binding.tvCategory.visibility = View.VISIBLE
        } else {
            holder.binding.ivCategory.setBackgroundResource(R.drawable.category_background)
            holder.binding.layoutCategory.setBackgroundResource(0)
            holder.binding.tvCategory.visibility = View.GONE
        }
    }

    // getItemCount method to return the number of items in the list
    override fun getItemCount(): Int = items.size
}
