package de.imedia24.shop

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.entity.ProductEntity.Companion.toProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.service.ProductService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@DataJpaTest
class RepositoriesTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val productRepository: ProductRepository) {

    @Test
    fun testAddProduct() {
        val sku = UUID.randomUUID().toString().substring(1..15)
        val productEntity = ProductEntity(sku, "Juergen", "Hoeller",
                BigDecimal.valueOf(50.6), ZonedDateTime.now(), ZonedDateTime.now())
        entityManager.persist(productEntity)
        entityManager.flush()
        val found = productRepository.findBySku(sku)
        Assertions.assertThat(found).isEqualTo(productEntity)
    }

    @Test
    fun `test find multiple products`() {

        val products: MutableList<ProductEntity> = mutableListOf()
        for (i in 1..6) {
            val sku = UUID.randomUUID().toString().substring(0..15)
            val productEntity = ProductEntity(sku, "Juergen", "Hoeller",
                    BigDecimal.valueOf(50.6), ZonedDateTime.now(), ZonedDateTime.now())
            products.add(productEntity)
            entityManager.persist(productEntity)
            entityManager.flush()
        }
        val found = productRepository.findAllBySkuIn(products.map { productEntity -> productEntity.sku })

        Assertions.assertThat(found.containsAll(products).and(products.containsAll(found)))
    }

    @Test
    fun `test partially update product`() {

        val sku = UUID.randomUUID().toString().substring(0..15)
        val productResponse = ProductResponse(sku, "Lorem Ipsum", "consectetur adipiscing elit. vulputate lectus in, vestibulum magna.",
                BigDecimal.valueOf(49.99))
        entityManager.persist(productResponse.toProductEntity())
        entityManager.flush()


        val newProd = productResponse.copy(name = "NEW LOREM IPSEM",
                description = "Lorem ipsum dolor sit amet, orci sem lacinia lorem, lorem vel nulla.",
                price = BigDecimal.valueOf(99.99))


        productRepository.save(newProd.toProductEntity())

        val foundProd = productRepository.findBySku(sku)
        Assertions.assertThat(foundProd != null)

        Assertions.assertThat(newProd).isEqualTo(foundProd!!.toProductResponse()).isNotEqualTo(productResponse)

    }
}