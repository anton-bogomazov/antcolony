package com.abogomazov.antsim.domain

import kotlin.random.Random
import kotlin.time.Duration

data class Simulation(
    val world: World,
    val tickRate: Duration,
    val spawnChance: Double = 0.05,
    val evaporationRate: Double = 0.05,
) {
    fun tick() {
        world.evaporatePheromones(evaporationRate)

        if (Random.nextDouble() <= spawnChance) {
            val anthills = world.objects.filterIsInstance<Anthill>()
            val newborn = WalkerAnt.spawn(anthills.random())
            world.walkers.add(newborn)
        }

        for (walker in world.walkers) {
            walker.step(world)
        }
    }
}