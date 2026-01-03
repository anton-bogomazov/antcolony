package com.abogomazov.antsim.domain.grid

fun Direction.toVector(): CubeVector = when (this) {
    Direction.E  -> CubeVector(1, -1, 0)
    Direction.NE -> CubeVector(1, 0, -1)
    Direction.NW -> CubeVector(0, 1, -1)
    Direction.W  -> CubeVector(-1, 1, 0)
    Direction.SW -> CubeVector(-1, 0, 1)
    Direction.SE -> CubeVector(0, -1, 1)
}

fun CubeVector.toDirectionOrNull(): Direction? {
    val normalizedV = this.normalizedOrNull() ?: return null

    return Direction.entries.firstOrNull { it.toVector() == normalizedV }
}