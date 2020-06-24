package com.example.parayo.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImageRepository : JpaRepository<ProductImage, Long> {

    fun findByImageIds(imageIds: List<Long>): List<ProductImage>
}