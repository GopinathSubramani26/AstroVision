package com.example.astrovision.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astrovision.model.Products
import com.example.astrovision.repository.ProductsRepository_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ScreenState {
    object Loading : ScreenState()
    object Success : ScreenState()
    object Error : ScreenState()
}

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository_Impl,
) : ViewModel() {

    companion object {
        private const val TAG = "ProductsViewModel"
    }

     val screenState = mutableStateOf<ScreenState>(ScreenState.Loading)
     val productsList = mutableStateOf<List<Products>>(emptyList())
     val originalProductsList = mutableStateOf<List<Products>>(emptyList())

    fun fetchProductsOnline(){
        screenState.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getProductsOnline()
                    .distinctUntilChanged()
                    .collect { productsModel -> }
                screenState.value = ScreenState.Success

            } catch (e: Exception) {
                Log.e(TAG, "Fetch Products Failed: ${e.message}")
                screenState.value = ScreenState.Error
            }
        }
    }

    fun fetchProductsOffline() {
        screenState.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getProductsOffline()
                    .distinctUntilChanged()
                    .collect { products ->
                        val productsInfoList = products.map { products ->
                            Products(
                                id = products.id,
                                productId = products.productId ?:"",
                                name = products.name ?:"",
                                description = products.description ?:"",
                                availableLanguages = products.availableLanguages,
                                image = products.imagePath.toString(),
                                authentic = products.authentic ?:"",
                                remedies = products.remedies ?:"",
                                vedic = products.vedic ?: "",
                                price = products.price ?:0,
                                discount = products.discount ?:0,
                                soldcount = products.soldcount,
                                avg = products.avg ?:0.0
                            )
                        }
                        Log.d(TAG, "Mapped products: $productsInfoList")
                        originalProductsList.value = productsInfoList
                        productsList.value = productsInfoList
                        screenState.value = ScreenState.Success

                    }
            } catch (e: Exception) {
                Log.e(TAG, "Fetch Products Failed: ${e.message}")
                screenState.value = ScreenState.Error
            }
        }
    }

    fun filterProducts(query: String) {
        if (query.isBlank()) {
            productsList.value = originalProductsList.value
        } else {
            productsList.value = originalProductsList.value.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}
