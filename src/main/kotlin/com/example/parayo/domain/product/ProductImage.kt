package com.example.parayo.domain.product

import com.example.parayo.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "product_image")
class ProductImage(
    private val path: String,
    private val productId: Long = 0L // FK
) : BaseEntity()