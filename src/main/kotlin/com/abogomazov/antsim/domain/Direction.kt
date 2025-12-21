package com.abogomazov.antsim.domain

enum class Direction(val delta: Hex) {
    E(Hex(1, -1, 0)),
    NE(Hex(1, 0, -1)),
    NW(Hex(0, 1, -1)),
    W(Hex(-1, 1, 0)),
    SW(Hex(-1, 0, 1)),
    SE(Hex(0, -1, 1));
}