package com.example.dorstip_app.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dorstip_app.cart.CartActivity
import com.example.dorstip_app.dashboard.BannerSlider.SliderAdapter
import com.example.dorstip_app.dashboard.BannerSlider.SliderModel
import com.example.dorstip_app.dashboard.Products.ItemModel
import com.example.dorstip_app.dashboard.shoppingcart.ShoppingCart
import com.example.dorstip_app.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding // Initialize ActivityDetailBinding
    private lateinit var item: ItemModel // Declare an instance of ItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        banners()
        setupSpinner()

        binding.btnAdd.setOnClickListener {
            val quantity = binding.spinnerQuantity.selectedItem.toString().toInt()
            addItemToCart(item, quantity)
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
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