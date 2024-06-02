package com.example.dorstip_app.dashboard.Products

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.dorstip_app.dashboard.Review.ReviewViewModel
import com.example.dorstip_app.databinding.ViewholderRecommendedBinding

// Adapter for displaying products in RecyclerView
class ProductAdapter(private var items: MutableList<ItemModel>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // ViewHolder class to hold the views
    class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)

    // ViewModel for fetching reviews
    private lateinit var reviewViewModel: ReviewViewModel

    // Create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderRecommendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Initialize reviewViewModel with ViewModelProvider
        reviewViewModel = ViewModelProvider(parent.context as AppCompatActivity).get(ReviewViewModel::class.java)
        return ViewHolder(binding)
    }

    // Bind data to ViewHolder views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Set product details
        holder.binding.tvDrinkName.text = item.title
        holder.binding.tvPrice.text = "€" + item.price.toString()
        holder.binding.tvRating.text = item.rating.toString()

        // Load product image using Glide
        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(requestOptions)
            .fitCenter()
            .into(holder.binding.ivDrink)

        // Set click listener to navigate to DetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", item)
            holder.itemView.context.startActivity(intent)
        }

        // Fetch and display the number of reviews
        reviewViewModel.fetchReviews(item.id.toString())
        reviewViewModel.reviews.observe(context as AppCompatActivity, { reviews ->
            holder.binding.tvReviewNumber.text = reviews.size.toString()
        })
    }

    // Return the number of items in the list
    override fun getItemCount(): Int = items.size

    // Update the list of items
    fun updateList(newItems: List<ItemModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
