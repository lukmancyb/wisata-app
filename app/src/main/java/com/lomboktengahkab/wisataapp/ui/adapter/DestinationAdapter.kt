package com.lomboktengahkab.wisataapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lomboktengahkab.wisataapp.Constants.IMAGE_URL_UNPLASH
import com.lomboktengahkab.wisataapp.R
import com.lomboktengahkab.wisataapp.data.Destination
import com.lomboktengahkab.wisataapp.databinding.DestinationItemBinding


class DestinationAdapter(private val items : List<Destination>) : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        return DestinationViewHolder(
            DestinationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class DestinationViewHolder(private val binding: DestinationItemBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bindItem(item : Destination){

            binding.destinationRatting.text = item.ratting.toString()
            val image = "$IMAGE_URL_UNPLASH${item.category_slug}"
            binding.destinationImage.load(image){
                placeholder(R.drawable.placeholder)
            }
            binding.destinationName.text = item.name

            binding.root.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }


        }
    }

    var onItemClickListener : ((Destination) -> Unit)? = null
}