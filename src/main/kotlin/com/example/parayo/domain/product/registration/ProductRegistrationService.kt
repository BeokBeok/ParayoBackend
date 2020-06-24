package com.example.parayo.domain.product.registration

import com.example.parayo.common.ParayoException
import com.example.parayo.domain.auth.UserContextHolder
import com.example.parayo.domain.product.Product
import com.example.parayo.domain.product.ProductImageRepository
import com.example.parayo.domain.product.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductRegistrationService @Autowired constructor(
    private val productRepository: ProductRepository,
    private val productImageRepository: ProductImageRepository,
    private val userContextHolder: UserContextHolder
) {

    fun register(request: ProductRegistrationRequest) {
        val images = findAndValidateImages(request.imageIds)
        request.validateRequest()
        request.toProduct(images, userContextHolder.id).run(::save)
            ?: throw ParayoException("상품 등록에 필요한 사용자 정보가 존재하지 않습니다.")
    }

    private fun findAndValidateImages(imageIds: List<Long>) =
        productImageRepository.findByImageIds(imageIds).also { images ->
            images.forEach { image ->
                if (image.productId != 0L) {
                    throw ParayoException("이미 등록된 상품입니다.")
                }
            }
        }

    private fun save(product: Product) = productRepository.save(product)
}