package com.example.dorstip_app.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.dorstip_app.Profile.ProfileActivity
import com.example.dorstip_app.cart.CartActivity
import com.example.dorstip_app.dashboard.BannerSlider.SliderAdapter
import com.example.dorstip_app.dashboard.BannerSlider.SliderModel
import com.example.dorstip_app.dashboard.Categories.CategoryAdapter
import com.example.dorstip_app.dashboard.Products.ProductAdapter
import com.example.dorstip_app.dashboard.Products.ProductListActivity
import com.example.dorstip_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel() // Create an instance of MainViewModel
    private lateinit var binding: ActivityMainBinding // Initialize ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner() // Initialize the banner
        initCategory() // Initialize the category
        initProduct() // Initialize the product items

        binding.btnDrinks.setOnClickListener {
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }

        binding.home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        binding.profile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    // Function to initialize the banner
    private fun initBanner() {
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
        })
        viewModel.loadBanners()
    }
    // Function to set up the banner with images
    private fun banners(images: List<SliderModel>) {
        // Set up ViewPager2 for the banner
        binding.vpBanner.adapter = SliderAdapter(images, binding.vpBanner)
        binding.vpBanner.clipToPadding = false
        binding.vpBanner.clipChildren = false
        binding.vpBanner.offscreenPageLimit = 3
        binding.vpBanner.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        // Apply a composite page transformer with margin to the ViewPager2
        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.vpBanner.setPageTransformer(compositePageTransformer)
        // If there are more than one image, show the indicator
        if (images.size > 1) {
            binding.diBanner.visibility = View.VISIBLE
            binding.diBanner.attachTo(binding.vpBanner)
        }
    }
    // Function to initialize the category
    private fun initCategory() {
        viewModel.categories.observe(this, Observer { categoryList ->
            // Set up RecyclerView for the category
            binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvCategory.adapter = CategoryAdapter(categoryList, this)
        })
        viewModel.loadCategory() // Load categories from ViewModel
    }
    // Function to initialize the product items
    private fun initProduct() {
        viewModel.products.observe(this, Observer { productList ->
            // Set up RecyclerView for recommended items
            binding.rvDrinks.layoutManager = GridLayoutManager(this, 2)
            binding.rvDrinks.adapter = ProductAdapter(productList, this)
        })
        viewModel.loadRecommended() // Load recommended items from ViewModel
    }
}
