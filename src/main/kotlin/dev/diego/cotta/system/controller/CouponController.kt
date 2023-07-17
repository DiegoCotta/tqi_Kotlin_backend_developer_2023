package dev.diego.cotta.system.controller

import dev.diego.cotta.system.dto.request.CouponDto
import dev.diego.cotta.system.dto.response.CouponView
import dev.diego.cotta.system.service.CouponService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponController(private val service: CouponService) {

    @PostMapping
    fun saveCoupon(@RequestBody @Valid couponDto: CouponDto): ResponseEntity<Any> {
        service.save(couponDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun getCoupons(): ResponseEntity<List<CouponView>> {
        return ResponseEntity.ok(service.listCoupons().map { CouponView(it) })
    }
}
