package dev.diego.cotta.system.entity

import com.fasterxml.jackson.annotation.JsonCreator
import dev.diego.cotta.system.exception.BusinessException


enum class MeasuringUnitType(val unitName: String) {
    PC("PC"),
    UN("UN"),
    KG("KG"),
    CT("CT");

    override fun toString(): String {
        return unitName
    }

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromStringUnit(unit: String): MeasuringUnitType =
            values().firstOrNull { it.unitName == unit } ?: throw BusinessException("Unidade de medida não existente")
    }


}
