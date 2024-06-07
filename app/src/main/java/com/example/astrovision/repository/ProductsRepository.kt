package com.example.astrovision.repository

import com.example.astrovision.localsource.ProductsEntity
import com.example.astrovision.model.ProductsResponse
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getProductsOnline(): Flow<ProductsResponse>

    suspend fun getProductsOffline(): Flow<List<ProductsEntity>>

    suspend fun getProductById(id: Int): Flow<ProductsEntity>

}

