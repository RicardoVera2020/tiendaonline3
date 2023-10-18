package com.ricardovera.cl.tiendaonline3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.ratingbar.RatingBar
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.producto)


        sharedPreferences = getSharedPreferences("ProductPreferences", Context.MODE_PRIVATE)


        val product = intent.getSerializableExtra("product") as Product


        loadRatingAndComment(product.title)

        // Mostrar los detalles del producto
        productNameTextView.text = product.title
        priceTextView.text = "$${product.price}"
        createdAtTextView.text = "Fecha de creación: ${product.createdAt}"

        // Configurar la calificación con SharedPreferences
        ratingBar.rating = getSavedRating(product.title)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            saveRating(product.title, rating)
        }

        //  botón de comentario y su acción
        commentButton.setOnClickListener {
            val comment = commentEditText.text.toString()
            saveComment(product.title, comment)
        }
    }

    private fun saveRating(productTitle: String, rating: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat("rating_$productTitle", rating)
        editor.apply()
    }

    private fun getSavedRating(productTitle: String): Float {
        return sharedPreferences.getFloat("rating_$productTitle", 0.0f)
    }

    private fun saveComment(productTitle: String, comment: String) {
        val editor = sharedPreferences.edit()
        editor.putString("comment_$productTitle", comment)
        editor.apply()
    }

    private fun loadRatingAndComment(productTitle: String) {
        val rating = getSavedRating(productTitle)
        ratingBar.rating = rating

        val comment = sharedPreferences.getString("comment_$productTitle", "")
        commentEditText.setText(comment)
    }
}
