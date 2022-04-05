package com.lomboktengahkab.wisataapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lomboktengahkab.wisataapp.R
import com.lomboktengahkab.wisataapp.data.Category
import com.lomboktengahkab.wisataapp.databinding.ItemMenuBinding

class CategoryAdapter( private val items: List<Category> ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding : ItemMenuBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindItem(item : Category){
            binding.categoryName.text = item.name
                item.image?.let {
                    binding.categoryImage.load(R.drawable.ic_bread)
            }
            binding.root.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(items[position])

    }

    override fun getItemCount(): Int = items.size


    var onItemClickListener : ((Category) -> Unit)? = null
}