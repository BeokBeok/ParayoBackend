package com.example.parayo.domain.product

import com.example.parayo.domain.jpa.BaseEntity
import javax.persistence.*

@Entity(name = "product")
class Product(
    @Column(length = 40)
    val name: String,
    @Column(length = 500)
    val description: String,
    val price: Int,
    val categoryId: Int,
    @Enumerated(EnumType.STRING)
    val status: ProductStatus,
    @OneToMany
    @JoinColumn(name = "productId") // FK 를 이용한 조인 쿼리
    val images: List<ProductImage>,
    val userId: Long
) : BaseEntity()