package com.abogomazov.antsim.domain.hex

fun Direction.toVector(): CubeVector = when (this) {
    Direction.E  -> CubeVector(1, -1, 0)
    Direction.NE -> CubeVector(1, 0, -1)
    Direction.NW -> CubeVector(0, 1, -1)
    Direction.W  -> CubeVector(-1, 1, 0)
    Direction.SW -> CubeVector(-1, 0, 1)
    Direction.SE -> CubeVector(0, -1, 1)
}

fun CubeCoordinate.deltaTo(other: CubeCoordinate): CubeVector =
    CubeVector(
        dx = other.x - x,
        dy = other.y - y,
        dz = other.z - z
    )

fun CubeVector.toDirectionOrNull(): Direction? =
    Direction.entries.firstOrNull { it.toVector() == this }