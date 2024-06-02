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

// DetailActivity displays detailed information about a product
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding // View binding for activity_detail.xml
    private lateinit var item: ItemModel // Holds the product item
    private val reviewViewModel: ReviewViewModel by viewModels() // View model for reviews
    private lateinit var reviewAdapter: ReviewAdapter // Adapter for displaying reviews

    // Called when the activity is starting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the activity
        getBundle() // Get product item from intent
        banners() // Set up product image banners
        setupSpinner() // Set up quantity spinner
        setupReviews() // Set up reviews RecyclerView

        // Handle add to cart button click
        binding.btnAdd.setOnClickListener {
            val quantity = binding.spinnerQuantity.selectedItem.toString().toInt() // Get selected quantity
            addItemToCart(item, quantity) // Add item to cart
            val intent = Intent(this, CartActivity::class.java) // Create intent for cart activity
            startActivity(intent) // Start cart activity
        }

        // Handle add review button click
        binding.btnAddReview.setOnClickListener {
            val reviewText = binding.etReview.text.toString().trim() // Get review text
            if (reviewText.isNotEmpty()) {
                reviewViewModel.addReview(item.id.toString(), reviewText) // Add review to view model
                binding.etReview.text.clear() // Clear review text field
            } else {
                Toast.makeText(this, "Please write a review", Toast.LENGTH_SHORT).show() // Show toast if review text is empty
            }
        }
    }

    // Set up product image banners
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

    // Set up reviews RecyclerView
    private fun setupReviews() {
        reviewAdapter = ReviewAdapter(emptyList())
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        binding.rvReviews.adapter = reviewAdapter

        reviewViewModel.fetchReviews(item.id.toString())

        reviewViewModel.reviews.observe(this, Observer { reviews ->
            reviewAdapter = ReviewAdapter(reviews)
            binding.rvReviews.adapter = reviewAdapter
        })
    }

    // Get product item from intent
    private fun getBundle() {
        item = intent.getParcelableExtra("object")!! // Get product item from intent

        // Set product details in UI
        binding.tvProductTitle.text = item.title
        binding.tvDescription.text = item.description
        binding.tvPrice.text = "€" + item.price
        binding.tvRating.text = "${item.rating}"
        binding.tvAlcohol.text = "Alcohol ${item.alcohol} %"
        binding.tvType.text = item.type
        binding.tvBrewery.text = item.brewery

        // Handle back button click
        binding.ibBack.setOnClickListener { finish() }
    }

    // Set up quantity spinner
    private fun setupSpinner() {
        val quantities = (1..10).toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, quantities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerQuantity.adapter = adapter
    }

    // Add item to shopping cart
    private fun addItemToCart(item: ItemModel, quantity: Int) {
        ShoppingCart.addItem(item, quantity)
    }
}
