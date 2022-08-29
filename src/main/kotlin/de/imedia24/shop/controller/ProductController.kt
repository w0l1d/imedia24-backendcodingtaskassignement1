package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @Operation(summary = "get product", description = "Returns 200 if successful")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "201", description = "Product is found and returned Successfully"),
                ApiResponse(responseCode = "404", description = "Product with the given sku not found"),
            ]
    )
    @GetMapping("/product/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductBySku(
            @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @Operation(summary = "get multiple products", description = "Returns 200 if successful")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "at least one the given sku's product is found and returned"),
                ApiResponse(responseCode = "404", description = "non of the given SKUs are found"),
            ]
    )
    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun getProductsBySku(
            @RequestParam("skus") skus: List<String>
    ): ResponseEntity<List<ProductResponse>> {
        logger.info("Request for products $skus")

        val product = productService.findAllProductsBySku(skus)

        return if(product.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }


    @Operation(summary = "Add new product", description = "Returns 201 if successful")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "201", description = "Product Created and returned Successfully"),
                ApiResponse(responseCode = "409", description = "Product with the given sku already exists"),
            ]
    )
    @PostMapping("/product", produces = ["application/json;charset=utf-8"])
    fun addProduct(
            @RequestBody product: ProductResponse
    ): ResponseEntity<Any> {
        logger.info("Adding product ${product.sku}")

        val createdProduct = productService.addProduct(product)
        return if(createdProduct == null) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("Product with sku (${product.sku}) already exists")
        } else {
            ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
        }
    }


    @Operation(summary = "Partially update product", description = "Returns 200 if successful")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "Product is found and updated Successfully"),
                ApiResponse(responseCode = "404", description = "Product with the given sku not found"),
            ]
    )
    @PatchMapping("/product/{sku}", produces = ["application/json;charset=utf-8"])
    fun updateProduct(
            @PathVariable("sku") sku: String,
            @RequestBody product: ProductResponse
    ): ResponseEntity<ProductResponse> {
        logger.info("updating product $sku")

        val updatedProduct = productService.updateProduct(sku, product)
        return if(updatedProduct == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(updatedProduct)
        }
    }
}
