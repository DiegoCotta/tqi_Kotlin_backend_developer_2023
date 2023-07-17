package dev.diego.cotta.system.controller

import dev.diego.cotta.system.dto.request.CategoryDto
import dev.diego.cotta.system.dto.response.CategoryView
import dev.diego.cotta.system.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/category")
class CategoryController(private val service: CategoryService) {

    @PostMapping
    fun saveCategory(@RequestBody @Valid categoryDto: CategoryDto): ResponseEntity<Any> {
        service.save(categoryDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun getCoupons(): ResponseEntity<List<CategoryView>> {
        return ResponseEntity.ok(service.findAll().map { CategoryView(it) })
    }

}
