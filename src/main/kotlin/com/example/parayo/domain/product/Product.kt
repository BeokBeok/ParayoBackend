package com.example.parayo.domain.product

import java.util.*
import javax.persistence.*

@Entity(name = "product")
class Product(
    @Column(length = 40)
    private val name: String,
    @Column(length = 500)
    private val description: String,
    private val price: Int,
    private val categoryId: Int,
    @Enumerated(EnumType.STRING)
    private val status: ProductStatus,
    @OneToMany
    @JoinColumn(name = "productId") // FK 를 이용한 조인 쿼리
    private val images: List<ProductImage>,
    private val userId: Long
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