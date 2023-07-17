package dev.diego.cotta.system.controller

import dev.diego.cotta.system.dto.request.ProductDto
import dev.diego.cotta.system.dto.request.ProductUpdateDto
import dev.diego.cotta.system.dto.response.ProductPrivateView
import dev.diego.cotta.system.dto.response.ProductPublicView
import dev.diego.cotta.system.service.CategoryService
import dev.diego.cotta.system.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/product")
class ProductController(val service: ProductService, val categoryService: CategoryService) {

    @PostMapping
    fun saveProduct(@RequestBody @Valid productDto: ProductDto): ResponseEntity<Any> {
        service.save(productDto.toEntity(categoryService.findById(productDto.categoryId)))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping
    fun updateProduct(@RequestBody @Valid productUpdateDto: ProductUpdateDto): ResponseEntity<ProductPrivateView> {
        val product = service.findById(productUpdateDto.id)
        productUpdateDto.categoryId?.let { product.category = categoryService.findById(it) }
        productUpdateDto.price?.let { product.price = it }
        productUpdateDto.quantity?.let { product.quantity = it }
        service.save(product)
        return ResponseEntity.ok(ProductPrivateView(product))
    }

    @GetMapping("/find/category/{category}")
    fun getProductsByCategory(@PathVariable category: String): ResponseEntity<List<ProductPublicView>> {
        return ResponseEntity.ok(service.findByCategoryName(category).map { ProductPublicView(it) })
    }

    @GetMapping("/find/name/{name}")
    fun getProductByName(@PathVariable name: String): ResponseEntity<List<ProductPublicView>> =
        ResponseEntity.ok(service.findByName(name).map { ProductPublicView(it) })

    @GetMapping("/find/id/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductPrivateView> =
        ResponseEntity.ok(ProductPrivateView(service.findById(id)))

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductPublicView>> =
        ResponseEntity.ok(service.findAll().map { ProductPublicView(it) })

}
