package com.example.astrovision.localsource

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "products")
@TypeConverters(Converters::class)
data class ProductsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: String?,
    val name: String?,
    val description: String?,
    val availableLanguages: List<String>,
    val pages: Int?,
    val pagesintext: String?,
    val reportType: String?,
    val authentic: String?,
    val remedies: String?,
    val vedic: String?,
    val price: Int?,
    val discount: Int?,
    val appDiscount: Int?,
    val couponDiscount: Int?,
    val imagePath: String?,
    val soldcount: String?,
    val avg: Double?
)
