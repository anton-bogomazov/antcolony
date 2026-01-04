package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.CubeCoordinate
import kotlin.math.min

const val MAX_PHEROMONE = 10.0

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
        val newAmount = amount + d
        amount = min(newAmount, MAX_PHEROMONE)
    }

    override fun toString(): String = "Pheromone[$amount] at $hex"
}