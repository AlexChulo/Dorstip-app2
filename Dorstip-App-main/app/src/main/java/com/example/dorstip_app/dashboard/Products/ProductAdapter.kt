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

class ProductAdapter(private var items: MutableList<ItemModel>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
        
    class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvDrinkName.text = items[position].title
        holder.binding.tvPrice.text = "€" + items[position].price.toString()
        holder.binding.tvRating.text = items[position].rating.toString()


        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .fitCenter()
            .into(holder.binding.ivDrink)


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
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