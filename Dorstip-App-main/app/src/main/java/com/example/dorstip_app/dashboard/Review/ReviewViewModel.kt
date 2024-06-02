package com.example.dorstip_app.dashboard.Review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

// ViewModel for managing reviews
class ReviewViewModel : ViewModel() {
    // Reference to the Firebase database
    private val database = FirebaseDatabase.getInstance().reference

    // LiveData for holding the list of reviews
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    // Method to fetch reviews for a specific item from the database
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
                _reviews.value = reviewList // Update LiveData with the list of reviews
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }

    // Method to add a review for a specific item to the database
    fun addReview(itemId: String, reviewText: String) {
        val review = Review(reviewText) // Create a Review object with the provided text
        database.child("Items").child(itemId).child("review").push().setValue(review) // Push the review to the database
    }
}