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

class ProductAdapter(private var items: MutableList<ItemModel>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var reviewViewModel: ReviewViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderRecommendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        reviewViewModel = ViewModelProvider(parent.context as AppCompatActivity).get(ReviewViewModel::class.java)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvDrinkName.text = item.title
        holder.binding.tvPrice.text = "€" + item.price.toString()
        holder.binding.tvRating.text = item.rating.toString()

        // Load the image using Glide
        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(requestOptions)
            .fitCenter()
            .into(holder.binding.ivDrink)

        // Fetch and display the number of reviews for this item
        reviewViewModel.fetchReviewCount(item.id.toString()) { count ->
            holder.binding.tvReviewNumber.text = count.toString()
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", item)
            holder.itemView.context.startActivity(intent)
        }

}

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<ItemModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
