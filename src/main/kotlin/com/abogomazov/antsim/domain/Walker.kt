package com.abogomazov.antsim.domain

sealed interface Walker {
    val hex: Hex
    val direction: Direction

    fun step(grid: Grid)
}

class DummyWalker(
    initHex: Hex,
    initDir: Direction,
) : Walker {
    override var hex: Hex = initHex
        private set

    override var direction: Direction = initDir
        private set

    override fun step(grid: Grid) {
        val nextHex = hex + direction.delta
        val nextHexObject = grid.getObject(nextHex)
        if (grid.contains(nextHex) && nextHexObject !is Obstacle) {
            hex = nextHex
        } else {
            direction = direction.cw()
            step(grid)
        }
    }
}