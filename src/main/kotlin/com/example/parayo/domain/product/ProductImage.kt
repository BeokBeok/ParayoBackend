package com.example.parayo.domain.product

import java.util.*
import javax.persistence.*

@Entity(name = "product_image")
class ProductImage(
    private val path: String,
    private val productId: Long = 0L // FK
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var createdAt: Date = Date(0)
    var updatedAt: Date = Date(0)

    @PrePersist
    fun prePersist() {
        createdAt = Date()
        updatedAt = Date()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Date()
    }
}