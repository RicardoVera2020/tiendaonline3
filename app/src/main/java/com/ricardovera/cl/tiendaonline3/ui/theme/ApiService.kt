package com.ricardovera.cl.tiendaonline3.ui.theme

import com.ricardovera.cl.tiendaonline3.model.Category
import com.ricardovera.cl.tiendaonline3.model.Product
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("api/v1/categories")
    fun getCategories(): Call<List<Category>>

    @GET("api/v1/products")
    fun getProducts(): Call<List<Product>>
}

