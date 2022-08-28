package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.entity.ProductEntity.Companion.toProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        return productRepository.findBySku(sku)?.toProductResponse()
    }


    fun findAllProductsBySku(sku: List<String>): List<ProductResponse> {
        return productRepository.findAllBySkuIn(sku).map { productEntity -> productEntity.toProductResponse() }
    }


    fun addProduct(product: ProductResponse): ProductResponse? {
        val checkProduct = productRepository.findBySku(product.sku)
        return if (checkProduct != null)
            null
        else
            productRepository.save(product.toProductEntity()).toProductResponse()
    }


    fun updateProduct(sku: String, product: ProductResponse): ProductResponse? {
        val checkProduct = productRepository.findBySku(sku)

        return if (checkProduct == null)
            null
        else
            productRepository
                    .save(product.toProductEntity()
                            .copy(sku = sku))
                    .toProductResponse()
    }
}
