package com.abogomazov.antsim.app.config.parser

import com.abogomazov.antsim.domain.grid.CubeCoordinate

data class AxialCoordinate(
    val r: Int,
    val q: Int,
) {
    fun toCube(): CubeCoordinate {
        val x = q
        val z = r
        val y = -x - z
        return CubeCoordinate(x, y, z)
    }
}