package com.example.astrovision

sealed class Screen(val route: String) {
    object ProductsHomeScreen : Screen("ProductsHomeScreen")
    object ProductDetailScreen : Screen("ProductDetailScreen")
}
