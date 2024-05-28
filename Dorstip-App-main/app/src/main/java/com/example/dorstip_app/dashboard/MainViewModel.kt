package com.example.dorstip_app.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dorstip_app.dashboard.BannerSlider.SliderModel
import com.example.dorstip_app.dashboard.Categories.CategoryModel
import com.example.dorstip_app.dashboard.Products.ItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel() : ViewModel() {
    // Connection to Firebase
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    // LiveData for holding category data
    private val category = MutableLiveData<MutableList<CategoryModel>>()

    // LiveData for holding banner data
    private val banner = MutableLiveData<List<SliderModel>>()

    // LiveData for holding recommended items data
    private val product = MutableLiveData<MutableList<ItemModel>>()

    // Expose categories LiveData to observe category data changes
    val categories: LiveData<MutableList<CategoryModel>> = category

    // Expose recommendation LiveData to observe recommended items data changes
    val products: LiveData<MutableList<ItemModel>> = product

    // Expose banners LiveData to observe banner data changes
    val banners: LiveData<List<SliderModel>> = banner



    // Function to load banners from Firebase
    fun loadBanners() {
        val Ref = firebaseDatabase.getReference("Banner")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }

    // Function to load categories from Firebase
    fun loadCategory() {
        val Ref = firebaseDatabase.getReference("Category")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(CategoryModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                category.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }

    // Function to load recommended items from Firebase
    fun loadRecommended() {
        val Ref = firebaseDatabase.getReference("Items")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                product.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }
}
