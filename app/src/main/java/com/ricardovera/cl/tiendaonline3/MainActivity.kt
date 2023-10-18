package com.ricardovera.cl.tiendaonline3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ricardovera.cl.tiendaonline3.model.Category
import com.ricardovera.cl.tiendaonline3.model.Product
import com.ricardovera.cl.tiendaonline3.ui.theme.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.escuelajs.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
        var products by remember { mutableStateOf<List<Product>>(emptyList()) }

        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(title = { Text(text = "Categorías") })

                if (categories.isEmpty()) {
                    LoadingView("Cargando categorías...")
                } else {
                    CategoryList(categories) { category ->
                        products = emptyList()
                        getProductsForCategory(category.id)
                    }
                }

                if (products.isEmpty()) {
                    LoadingView("Cargando productos...")
                } else {
                    ProductList(products)
                }
            }
        }


        getCategories()
    }

    private fun getCategories() {
        apiService.getCategories().enqueue(ApiCallback { response ->
            if (response.isSuccessful) {
                categories = response.body() ?: emptyList()
            } else {
                // Manejar errores
            }
        })
    }

    private fun getProductsForCategory(categoryId: String) {
        apiService.getProductsForCategory(categoryId).enqueue(ApiCallback { response ->
            if (response.isSuccessful) {
                products = response.body() ?: emptyList()
            } else {

            }
        })
    }
}
