package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.hex.CubeCoordinate

class Food(
    position: CubeCoordinate,
    amount: UInt,
) : ImmovableWorldObject(position) {

    var amount: UInt = amount
        private set

    fun take(amountTaken: UInt): UInt {
        val actualTaken = minOf(amountTaken, amount)
        amount -= actualTaken
        return actualTaken
    }
}