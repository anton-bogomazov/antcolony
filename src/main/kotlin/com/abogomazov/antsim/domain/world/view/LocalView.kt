package com.abogomazov.antsim.domain.world.view

interface LocalView {
    fun neighbors(): List<CellSense>
}
