package com.dipuguide.shopsee.domain.model

data class CartItem(
    val productId: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val quantity: Int = 1,
    val imageUrl: String = ""
)
