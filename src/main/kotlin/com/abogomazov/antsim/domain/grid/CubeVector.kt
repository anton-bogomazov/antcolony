package com.abogomazov.antsim.domain.grid

import kotlin.math.absoluteValue

data class CubeVector(
    val dx: Int,
    val dy: Int,
    val dz: Int,
) {
    init {
        require(dx + dy + dz == 0) { "Invalid cube vector" }
    }

    companion object {
        fun between(from: CubeCoordinate, to: CubeCoordinate) =
            CubeVector(
                to.x - from.x,
                to.y - from.y,
                to.z - from.z
            )

        val ZERO = CubeVector(0, 0, 0)
    }

    fun opposite(): CubeVector =
        CubeVector(-dx, -dy, -dz)

    fun isZero() = this == ZERO

    fun normalizedOrNull(): CubeVector? {
        if (isZero()) return null

        val length = maxOf(
            dx.absoluteValue,
            dy.absoluteValue,
            dz.absoluteValue
        )

        if (dx % length != 0 || dy % length != 0 || dz % length != 0) return null

        return CubeVector(
            dx = dx / length,
            dy = dy / length,
            dz = dz / length
        )
    }
}