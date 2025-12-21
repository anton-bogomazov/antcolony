package com.abogomazov.antsim.domain

enum class Direction(val delta: Hex) {
    E(Hex(1, -1, 0)),
    NE(Hex(1, 0, -1)),
    NW(Hex(0, 1, -1)),
    W(Hex(-1, 1, 0)),
    SW(Hex(-1, 0, 1)),
    SE(Hex(0, -1, 1));

    fun cw(): Direction =
        entries[(ordinal + 1) % entries.size]

    fun ccw(): Direction =
        entries[(ordinal + entries.size - 1) % entries.size]
}