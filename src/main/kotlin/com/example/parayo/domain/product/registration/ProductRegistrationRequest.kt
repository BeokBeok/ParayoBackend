package com.example.parayo.domain.product.registration

import com.example.parayo.common.ParayoException
import com.example.parayo.domain.product.Product
import com.example.parayo.domain.product.ProductImage
import com.example.parayo.domain.product.ProductStatus

data class ProductRegistrationRequest(
    val name: String,
    val description: String,
    val price: Int,
    val categoryId: Int,
    val imageIds: List<Long>
) {

    fun validateRequest() = when {
        name.length !in 1..40 ||
                imageIds.size !in 1..4 ||
                imageIds.isEmpty() ||
                description.length !in 1..500 ||
                price <= 0 -> throw ParayoException("올바르지 않은 상품 정보입니다.")
        else -> Unit
    }

    fun toProduct(images: List<ProductImage>, userId: Long) = Product(
        name = name,
        description = description,
        price = price,
        categoryId = categoryId,
        status = ProductStatus.SELLABLE,
        images = images,
        userId = userId
    )
}