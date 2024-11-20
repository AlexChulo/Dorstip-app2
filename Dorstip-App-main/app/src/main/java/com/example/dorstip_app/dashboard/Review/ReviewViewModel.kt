package com.example.dorstip_app.dashboard.Review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class ReviewViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference
    private val reviewData = mutableMapOf<String, MutableLiveData<List<Review>>>()

    fun getReviews(itemId: String): LiveData<List<Review>> {
        if (!reviewData.containsKey(itemId)) {
            reviewData[itemId] = MutableLiveData()
            fetchReviews(itemId)
        }
        return reviewData[itemId]!!
    }

    private fun fetchReviews(itemId: String) {
        database.child("Items").child(itemId).child("review").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reviewList = mutableListOf<Review>()
                for (reviewSnapshot in snapshot.children) {
                    val review = reviewSnapshot.getValue(Review::class.java)
                    if (review != null) {
                        reviewList.add(review)
                    }
                }
                reviewData[itemId]?.value = reviewList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }
    fun fetchReviewCount(itemId: String, callback: (Int) -> Unit) {
        database.child("Items").child(itemId).child("review").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val count = snapshot.childrenCount.toInt()
                callback(count)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors here
                callback(0)
            }
        })
    }


    fun addReview(itemId: String, reviewText: String) {
        val review = Review(reviewText)
        database.child("Items").child(itemId).child("review").push().setValue(review)
    }
}
