package com.example.dorstip_app.dashboard.Review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dorstip_app.databinding.ViewholderReviewBinding

// Adapter for displaying reviews in RecyclerView
class ReviewAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    // Create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ViewholderReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    // Bind data to ViewHolder views
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    // Return the number of items in the list
    override fun getItemCount() = reviews.size

    // ViewHolder class to hold the views
    class ReviewViewHolder(private val binding: ViewholderReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        // Bind review data to views
        fun bind(review: Review) {
            binding.tvReviewText.text = review.text // Set review text
        }
    }
}
