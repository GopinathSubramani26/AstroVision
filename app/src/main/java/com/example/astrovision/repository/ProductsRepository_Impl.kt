package com.example.astrovision.repository

import com.example.astrovision.localsource.ProductsDao
import com.example.astrovision.localsource.ProductsEntity
import com.example.astrovision.model.ProductsResponse
import com.example.astrovision.networksource.ProductsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepository_Impl @Inject constructor(
    private val productsService: ProductsService,
    private val productsDao: ProductsDao
) : ProductsRepository {

    companion object {
        const val TAG = "ProductsRepository_Impl"
    }

    override suspend fun getProductsOnline(): Flow<ProductsResponse> = flow {
        try {
            val response = productsService.getProducts()
            if (response.isSuccessful) {
                response.body()?.let { productsModel ->

                    val productsEntities = productsModel.products.map { (productId, product) ->
                        ProductsEntity(
                            productId = productId,
                            name = product.name,
                            description = product.description,
                            availableLanguages = product.availableLanguages,
                            pages = product.pages,
                            pagesintext = product.pagesintext,
                            reportType = product.reportType,
                            authentic = product.authentic,
                            remedies = product.remedies,
                            vedic = product.vedic,
                            price = product.price,
                            discount = product.discount,
                            appDiscount = product.appDiscount,
                            couponDiscount = product.couponDiscount,
                            imagePath = product.imagePath.square,
                            soldcount = product.soldcount,
                            avg = product.avg
                        )
                    }
                    withContext(Dispatchers.IO) {
                        productsDao.insertProducts(productsEntities)
                    }
                    emit(productsModel)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getProductsOffline(): Flow<List<ProductsEntity>> = flow {
        try {
            val products = productsDao.getProducts()
            emit(products)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getProductById(id: Int): Flow<ProductsEntity> = flow{
        try {
            val product = productsDao.getProductById(id)
            emit(product)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
