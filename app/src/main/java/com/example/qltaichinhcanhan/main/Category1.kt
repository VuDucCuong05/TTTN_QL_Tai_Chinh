package com.example.qltaichinhcanhan.main

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Category")
data class Category1(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int,
    var name: String? = null,
    var type: Int? = null, // 1: expense 2: income
    var lave: Float? = null,
    var icon: Int? = null,
    var color: Int? = null,
    var select: Boolean? = null,
) : Parcelable