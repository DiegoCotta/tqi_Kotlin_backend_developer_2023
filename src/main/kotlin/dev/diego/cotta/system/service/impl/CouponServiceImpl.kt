package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.repository.CouponRepository
import dev.diego.cotta.system.service.CouponService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CouponServiceImpl(private val repository: CouponRepository) : CouponService {
    override fun save(coupon: Coupon): Coupon =
        repository.save(coupon)

    override fun listCoupons(): List<Coupon> =
        repository.findAll()

    override fun findByCode(code: String): Coupon =
        repository.findByCode(code) ?: throw BusinessException("Cupom Inválido!")
}
