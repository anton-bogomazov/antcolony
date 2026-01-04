package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.CubeCoordinate

class Pheromone(
    hex: CubeCoordinate,
    amount: Double,
) : ImmovableWorldObject(hex) {

    var amount: Double = amount
        private set

    fun evaporate(rate: Double) {
        amount *= 1.0 - rate
    }

    fun deposit(d: Double) {
        amount += d
    }

    override fun toString(): String = "Pheromone[$amount] at $hex"
}