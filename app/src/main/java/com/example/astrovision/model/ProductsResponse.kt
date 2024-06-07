package com.example.astrovision.model

data class ProductsResponse(
    val products: Map<String, Product>
)

data class Product(
    val name: String,
    val description: String,
    val availableLanguages: List<String>,
    val sampleReports: Map<String, String>,
    val pages: Int,
    val pagesintext: String,
    val reportType: String,
    val authentic: String,
    val remedies: String,
    val vedic: String,
    val price: Int,
    val discount: Int,
    val appDiscount: Int,
    val couponDiscount: Int,
    val imagePath: ImagePath,
    val soldcount: String?,
    val avg: Double
)

data class ImagePath(
    val square: String,
    val wide: String
)

data class Products(
    val id: Int,
    val productId: String,
    val name: String,
    val description: String,
    val availableLanguages: List<String>,
    val image: String,
    val authentic: String,
    val remedies: String,
    val vedic: String,
    val price: Int,
    val discount: Int,
    val soldcount: String?,
    val avg: Double
)