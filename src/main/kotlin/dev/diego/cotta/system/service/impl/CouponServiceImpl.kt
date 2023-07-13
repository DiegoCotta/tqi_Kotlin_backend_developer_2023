package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.repository.CouponRepository
import dev.diego.cotta.system.service.CouponService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CouponServiceImpl(val repository: CouponRepository) : CouponService {
    override fun save(coupon: Coupon): Coupon =
        repository.save(coupon)


    override fun listCoupons(): List<Coupon> =
        repository.findAll(Sort.by(Sort.Direction.DESC, "name"))

    override fun findByCode(code: String): Coupon =
        repository.findByCode(code) ?: throw RuntimeException("Cupom Inválido!")
}