package com.abogomazov.antsim.domain

class DummyWalker(
    initHex: Hex,
    initDir: Direction,
) : Walker {
    override var hex: Hex = initHex
        private set

    override var direction: Direction = initDir
        private set

    override fun step(world: World) {
        val nextHex = hex + direction.delta
        val nextHexObject = world.getObject(nextHex)
        if (world.grid.contains(nextHex) && nextHexObject !is Obstacle) {
            hex = nextHex
        } else {
            direction = direction.cw()
            step(world)
        }
    }
}