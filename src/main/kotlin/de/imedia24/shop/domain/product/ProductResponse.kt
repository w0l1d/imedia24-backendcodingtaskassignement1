package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal


@Schema(description = "Model of a Product.")
data class ProductResponse(
        @field:Schema(
                description = "Identifier of a product",
                example = "123",
                type = "String",
                maxLength = 16
        )
        val sku: String,
        @field:Schema(
                description = "Name of a product",
                example = "Printing Paper",
                type = "String"
        )
        val name: String,
        @field:Schema(
                description = "Description of a product",
                example = "Printing Paper is a high quality printer" +
                        " paper made from 100% recycled fiber.",
                type = "String",
                maxLength = 125
        )
        val description: String,
        @field:Schema(
                description = "Product Price",
                example = "99.9",
                type = "BigDecimal"
        )
        val price: BigDecimal
) {
    companion object {
        fun ProductEntity.toProductResponse() = ProductResponse(
                sku = sku,
                name = name,
                description = description ?: "",
                price = price
        )
    }
}
