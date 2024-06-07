package com.example.astrovision.networksource

import com.example.astrovision.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductsService {

    @GET("test/products.php")
    suspend fun getProducts():Response<ProductsResponse>

}