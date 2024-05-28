package com.example.dorstip_app.OnboardingScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dorstip_app.R

class OnboardingItemsAdapter(private val onboardingItem: List<OnboardingItem>) :
RecyclerView.Adapter<OnboardingItemsAdapter.OnboardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        return OnboardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container,
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onboardingItem[position])
    }

    override fun getItemCount(): Int {
        return onboardingItem.size
    }
//    Initializing with the onboarding_item_container.xml
    inner class OnboardingItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val ivOnboarding = view.findViewById<ImageView>(R.id.ivOnboard)
        private val tvOnboardingTitle = view.findViewById<TextView>(R.id.tvOnboardTitle)
        private val tvOnboardingDescription = view.findViewById<TextView>(R.id.tvOnboardDescription)
//        Binding from the dataclass to the adapter
        fun bind(onboardingItem: OnboardingItem){
            ivOnboarding.setImageResource(onboardingItem.onboardingImage)
            tvOnboardingTitle.text = onboardingItem.onboardingTitle
            tvOnboardingDescription.text = onboardingItem.onboardingDescription
        }
    }
}