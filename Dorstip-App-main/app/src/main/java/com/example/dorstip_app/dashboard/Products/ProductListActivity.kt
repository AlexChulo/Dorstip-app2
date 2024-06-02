package com.example.dorstip_app.dashboard.Products

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dorstip_app.dashboard.MainViewModel
import com.example.dorstip_app.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProductList()
        initSearchBar()
    }


    private fun initProductList() {
        adapter = ProductAdapter(mutableListOf())
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = adapter

        viewModel.products.observe(this, Observer {
            adapter.updateList(it)
        })
        viewModel.loadRecommended()
    }


    private fun initSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun filterProducts(query: String) {
        val filteredList = if (query.isBlank()) {

            viewModel.products.value ?: mutableListOf()
        } else {

            viewModel.products.value?.filter {
                it.title.contains(query, true) || it.type.contains(query, true)
            } ?: mutableListOf()
        }
        adapter.updateList(filteredList)
    }
}