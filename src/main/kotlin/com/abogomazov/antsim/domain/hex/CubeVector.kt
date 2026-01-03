package com.abogomazov.antsim.domain.hex

data class CubeVector(
    val dx: Int,
    val dy: Int,
    val dz: Int,
) {
    init {
        require(dx + dy + dz == 0) { "Invalid cube vector" }
    }

    fun opposite(): CubeVector =
        CubeVector(-dx, -dy, -dz)
}