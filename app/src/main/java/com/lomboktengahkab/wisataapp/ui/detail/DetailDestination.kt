package com.lomboktengahkab.wisataapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.lomboktengahkab.wisataapp.Constants
import com.lomboktengahkab.wisataapp.R
import com.lomboktengahkab.wisataapp.data.Destination
import com.lomboktengahkab.wisataapp.databinding.ActivityDetailDestinationBinding

class DetailDestination : AppCompatActivity() {

    private lateinit var binding : ActivityDetailDestinationBinding


    companion object{
        const val EXTRA_DESTINATION_PARCELABLE = "extra_destination_parcalabel"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Destination>(EXTRA_DESTINATION_PARCELABLE)

        data?.let {
            binding.imgDetailDestination.load("${Constants.IMAGE_URL_UNPLASH}${it.category_slug}"){
                placeholder(R.drawable.placeholder)
            }
            binding.txtDetailName.text = it.name
            binding.txtDetailDescription.text = it.description
            binding.txtDetailCategory.text = it.category
            binding.txtDetailRatting.text = it.ratting?.toBigDecimal()?.toPlainString() ?: "0"
            binding.txtDestinationLocation.text = it.locationName
        }

    }
}