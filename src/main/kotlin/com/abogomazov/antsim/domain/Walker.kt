package com.abogomazov.antsim.domain

import kotlin.random.Random

sealed interface Walker {
    val hex: Hex
    val direction: Direction

    fun step(world: World)
}

// FIXME make cons private
class WalkerAnt(
    override var hex: Hex,
    override var direction: Direction
) : Walker {
    companion object {
        fun spawn(anthill: Anthill): WalkerAnt =
            WalkerAnt(
                hex = anthill.hex,
                direction = Direction.entries.random()
            )
    }

    var carryingFood: Boolean = false
        private set

    private fun moveToHex(target: Hex) {
        val delta = target - hex

        direction = Direction.entries.firstOrNull { it.delta == delta } ?: direction
        hex = target
    }

    override fun step(world: World) {
        val neighbors = world.grid.neighbors(hex)
            .filter { world.getObject(it) !is Obstacle }
            .filter { it !in world.walkers.map { it.hex } }

        if (neighbors.isEmpty()) return

        if (world.getObject(hex) is Anthill) {
            carryingFood = false
        }

        // FIXME orient to the moving dir
        if (carryingFood) {
            // возвращаемся к ближайшему муравейнику
            val anthills = world.objects.filterIsInstance<Anthill>()
            if (anthills.isNotEmpty()) {
                val target = anthills.minByOrNull { hex.distance(it.hex) }!!.hex
                val nextHex = neighbors.minByOrNull { it.distance(target) }!!
                moveToHex(nextHex)
                world.depositPheromone(hex, amount = 1.0) // оставляем феромон
            } else {
                moveToHex(neighbors.random())
            }
        } else {
            // ищем еду рядом
            val foodNeighbor = neighbors.firstOrNull { world.getObject(it) is Food }
            if (foodNeighbor != null) {
                moveToHex(foodNeighbor)
                val food = world.getObject(hex) as Food
                carryingFood = true
                food.amount -= 1u
                if (food.amount <= 0u) {
                    world.clear(hex)
                }
                world.depositPheromone(hex, amount = 0.8)
            } else {
                val totalPheromone = neighbors.sumOf { (world.pheromones[it] ?: 0.0) + 0.2 } // 0.2 - exploration chance
                val r = Random.nextDouble() * totalPheromone
                var acc = 0.0
                val nextHex = neighbors.firstOrNull {
                    acc += (world.pheromones[it] ?: 0.0) + 0.1
                    acc >= r
                } ?: neighbors.random()

                moveToHex(nextHex)
            }
        }
    }
}
