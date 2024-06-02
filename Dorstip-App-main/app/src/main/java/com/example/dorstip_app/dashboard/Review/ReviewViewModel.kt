package com.example.dorstip_app.dashboard.Review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class ReviewViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    fun fetchReviews(itemId: String) {
        database.child("Items").child(itemId).child("review").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reviewList = mutableListOf<Review>()
                for (reviewSnapshot in snapshot.children) {
                    val review = reviewSnapshot.getValue(Review::class.java)
                    if (review != null) {
                        reviewList.add(review)
                    }
                }
                _reviews.value = reviewList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }

    fun addReview(itemId: String, reviewText: String) {
        val review = Review(reviewText)
        database.child("Items").child(itemId).child("review").push().setValue(review)
    }
}

