package com.example.dorstip_app.dashboard.Products

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dorstip_app.dashboard.MainViewModel
import com.example.dorstip_app.databinding.ActivityProductListBinding

// Activity for displaying a list of products
class ProductListActivity : AppCompatActivity() {
    private val viewModel = MainViewModel() // ViewModel for managing product data
    private lateinit var binding: ActivityProductListBinding // View binding for activity_product_list.xml
    private lateinit var adapter: ProductAdapter // Adapter for displaying products in RecyclerView

    // Called when the activity is starting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProductList() // Initialize product list RecyclerView
        initSearchBar() // Initialize search bar

        // Automatically fill the search bar with the category from the intent
        val categoryTitle = intent.getStringExtra("CATEGORY_TITLE") ?: ""
        binding.searchBar.setText(categoryTitle)
    }

    // Initialize product list RecyclerView
    private fun initProductList() {
        adapter = ProductAdapter(mutableListOf(), this)
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = adapter
        binding.ibBack.setOnClickListener { finish() } // Handle back button click

        // Observe changes in product data and update the list
        viewModel.products.observe(this, Observer { products ->
            adapter.updateList(products)

            // Filter products based on the initial category title
            val categoryTitle = intent.getStringExtra("CATEGORY_TITLE") ?: ""
            filterProducts(categoryTitle)
        })

        viewModel.loadRecommended() // Load recommended products
    }

    // Initialize search bar
    private fun initSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString()) // Filter products based on search query
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Filter products based on query
    private fun filterProducts(query: String) {
        val filteredList = if (query.isBlank()) {
            viewModel.products.value ?: mutableListOf() // Return all products if query is blank
        } else {
            viewModel.products.value?.filter {
                it.title.contains(query, true) || it.type.contains(query, true) // Filter products by title or type
            } ?: mutableListOf()
        }
        adapter.updateList(filteredList) // Update the list with filtered products
    }
}
