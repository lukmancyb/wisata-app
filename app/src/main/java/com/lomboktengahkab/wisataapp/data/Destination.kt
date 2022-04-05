package com.lomboktengahkab.wisataapp.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val id : Int?,
    val name: String?,
    val description: String?,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("location_name")
    val locationName: String?,
    @SerializedName("rattings")
    val ratting: Double?,
    val category: String?,
    val category_slug: String?,
    val category_id : Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
) : Parcelable