package com.example.astrovision.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astrovision.model.Products
import com.example.astrovision.repository.ProductsRepository_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
private val repository: ProductsRepository_Impl,
) : ViewModel() {

    companion object {
        private const val TAG = "ProductDetailViewModel"
    }

    val product = mutableStateOf<Products?>(null)

    fun fetchProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getProductById(id).collect{ productEntity->
                    val productModel = Products(
                        id = productEntity.id,
                        productId = productEntity.productId ?: "",
                        name = productEntity.name ?: "",
                        description = productEntity.description ?: "",
                        availableLanguages = productEntity.availableLanguages,
                        image = productEntity.imagePath.toString(),
                        authentic = productEntity.authentic ?: "",
                        remedies = productEntity.remedies ?: "",
                        vedic = productEntity.vedic ?: "",
                        price = productEntity.price ?: 0,
                        discount = productEntity.discount ?: 0,
                        soldcount = productEntity.soldcount,
                        avg = productEntity.avg ?: 0.0
                    )

                    product.value = productModel

                }
            } catch (e: Exception) {
                Log.e(TAG, "Fetch Product Failed: ${e.message}")

            }
        }
    }
}
