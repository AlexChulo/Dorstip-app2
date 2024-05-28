package com.example.dorstip_app.dashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dorstip_app.dashboard.BannerSlider.SliderAdapter
import com.example.dorstip_app.dashboard.BannerSlider.SliderModel
import com.example.dorstip_app.dashboard.Products.ItemModel
import com.example.dorstip_app.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val viewModel = MainViewModel() // Initialize MainViewModel
    private lateinit var binding: ActivityDetailBinding // Initialize ActivityDetailBinding
    private lateinit var item: ItemModel // Declare an instance of ItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle() // Call getBundle() to retrieve data from intent
        banners() // Call banners() to set up image slider
    }

    // Function to set up the image slider
    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        // Set up ViewPager2 with SliderAdapter
        binding.vpProductImage.adapter = SliderAdapter(sliderItems, binding.vpProductImage)
        binding.vpProductImage.clipToPadding = false
        binding.vpProductImage.clipChildren = false
        binding.vpProductImage.offscreenPageLimit = 3
        binding.vpProductImage.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // Show indicator if there are more than one image
        if (sliderItems.size > 1) {
            binding.diProductImage.visibility = View.VISIBLE
            binding.diProductImage.attachTo(binding.vpProductImage)
        }
    }

    // Function to retrieve data from intent
    private fun getBundle() {
        // Retrieve item data passed from previous activity
        item = intent.getParcelableExtra("object")!!

        // Set up UI with item data
        binding.tvProductTitle.text = item.title
        binding.tvDescription.text = item.description
        binding.tvPrice.text = "€" + item.price
        binding.tvRating.text = "${item.rating}"
        binding.tvAlcohol.text = "Alcohol ${item.alcohol} %"
        binding.tvType.text = item.type
        binding.tvBrewery.text = item.brewery

        // Set up click listener for back button
        binding.ibBack.setOnClickListener { finish() }
    }
}