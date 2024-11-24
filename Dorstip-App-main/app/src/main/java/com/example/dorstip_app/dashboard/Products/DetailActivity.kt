package com.example.dorstip_app.dashboard.Products

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorstip_app.cart.CartActivity
import com.example.dorstip_app.dashboard.BannerSlider.SliderAdapter
import com.example.dorstip_app.dashboard.BannerSlider.SliderModel
import com.example.dorstip_app.dashboard.Review.ReviewAdapter
import com.example.dorstip_app.dashboard.Review.ReviewViewModel
import com.example.dorstip_app.dashboard.shoppingcart.ShoppingCart
import com.example.dorstip_app.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemModel
    private val reviewViewModel: ReviewViewModel by viewModels()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        banners()
        setupSpinner()
        setupReviews()

        binding.btnAdd.setOnClickListener {
            val quantity = binding.spinnerQuantity.selectedItem.toString().toInt()
            addItemToCart(item, quantity)
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnAddReview.setOnClickListener {
            val reviewText = binding.etReview.text.toString().trim()
            if (reviewText.isNotEmpty()) {
                reviewViewModel.addReview(item.id.toString(), reviewText)
                binding.etReview.text.clear()
            } else {
                Toast.makeText(this, "Please write a review", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.vpProductImage.adapter = SliderAdapter(sliderItems, binding.vpProductImage)
        binding.vpProductImage.clipToPadding = false
        binding.vpProductImage.clipChildren = false
        binding.vpProductImage.offscreenPageLimit = 3
        binding.vpProductImage.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        if (sliderItems.size > 1) {
            binding.diProductImage.visibility = View.VISIBLE
            binding.diProductImage.attachTo(binding.vpProductImage)
        }
    }
    private fun setupReviews() {
        reviewAdapter = ReviewAdapter(emptyList())
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        binding.rvReviews.adapter = reviewAdapter

        // Fetch reviews for this specific item using the isolated data in ViewModel
        reviewViewModel.getReviews(item.id.toString()).observe(this, Observer { reviews ->
            reviewAdapter.submitList(reviews)
        })
    }
    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.tvProductTitle.text = item.title
        binding.tvDescription.text = item.description
        binding.tvPrice.text = "€" + item.price
        binding.tvRating.text = "${item.rating}"
        binding.tvAlcohol.text = "Alcohol ${item.alcohol} %"
        binding.tvType.text = item.type
        binding.tvBrewery.text = item.brewery

        binding.ibBack.setOnClickListener { finish() }
    }

    private fun setupSpinner() {
        val quantities = (1..10).toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, quantities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerQuantity.adapter = adapter
    }

    private fun addItemToCart(item: ItemModel, quantity: Int) {
        ShoppingCart.addItem(item, quantity)
    }
}
