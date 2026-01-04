package com.abogomazov.antsim.domain.world

import com.abogomazov.antsim.app.config.WorldParameters
import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.grid.toVector
import com.abogomazov.antsim.domain.world.objects.*
import kotlin.random.Random

const val PHEROMONE_PERCEPTION_THRESHOLD = 0.1
const val FOOD_FOR_MAX_CHANCE = 20.0

class World(
    private val registry: ObjectRegistry,
    private val slicer: WorldSlicer,
    private val params: WorldParameters,
) {
    init {
        val objects = registry.objects()
        require(objects.any { it is Anthill }) {
            "The world should have at least one anthill"
        }
        objects.groupBy { it.hex }.forEach { (coordinate, objects) ->
            val types = objects.map { it::class }
            require(types.size == types.toSet().size) {
                "Multiple objects of the same type in hex $coordinate: $types"
            }

            val hasObstacle = objects.any { it is Obstacle }
            require(!hasObstacle || objects.size == 1) {
                "Obstacle cannot share hex $coordinate with other objects: $objects"
            }
        }
        println(registry.objects())
    }

    fun tick() {
        evaporatePheromones(params.evaporationRate)
        maybeSpawnWalker(params.spawnChanceModifier)

        val walkers = registry.objects().filterIsInstance<Walker>()

        for (walker in walkers) {
            val view = slicer.localViewFor(walker)
            println("$walker is seeing ${view.neighbors()}")
            val actions = walker.decide(view)
            println("$walker decided to $actions")
            actions.forEach { apply(walker, it) }
        }
    }

    private fun apply(walker: Walker, action: Action) {
        when (action) {
            Action.Idle -> Unit
            Action.PickFood -> pickFood(walker)
            Action.DropFood -> dropFood(walker)
            is Action.Move -> moveWalker(walker, action.direction)
            is Action.DepositPheromone -> depositPheromone(walker, action.amount)
        }
    }

    private fun moveWalker(walker: Walker, to: Direction) {
        val targetHex = walker.hex + to.toVector()
        val objects = registry.objectsAt(targetHex)
        if (objects.any { it is Walker || it is Obstacle }) {
            error("$walker tries to move on the obstacle or another walker")
        }

        registry.move(walker, targetHex)
        walker.hex = targetHex
        walker.orientation = to
    }

    private fun pickFood(walker: Walker) {
        val foods = registry.objectsAt(walker.hex).filterIsInstance<Food>()
        check(foods.size == 1) { "A single food is expected at ${walker.hex}"}
        val food = foods.single()
        if (food.amount == 0u) {
            error("$walker is tried to collect food, but food amount is zero!")
        }

        walker.carryingFood = true
        food.take(1u)
        println("$walker picked $food")
        if (food.amount == 0u) {
            registry.clear(food)
        }
    }

    private fun dropFood(walker: Walker) {
        if (!walker.carryingFood) {
            error("$walker is not carrying food, but tried to drop it!")
        }

        val anthills = registry.objectsAt(walker.hex).filterIsInstance<Anthill>()
        check(anthills.size == 1) { "A single anthill is expected at ${walker.hex}"}
        val anthill = anthills.single()

        walker.carryingFood = false
        anthill.store(1u)
        println("$walker dropped food at $anthill")
    }

    private fun depositPheromone(walker: Walker, amount: Double) {
        val pheromoneTrail = registry.objectsAt(walker.hex).filterIsInstance<Pheromone>()
        val pheromone = pheromoneTrail.firstOrNull()
        if (pheromone == null) {
            registry.add(Pheromone(walker.hex, amount))
        } else {
            pheromone.deposit(amount)
        }
    }

    private fun evaporatePheromones(rate: Double) {
        registry.objects().filterIsInstance<Pheromone>()
            .forEach {
                it.evaporate(rate)
                if (it.amount < PHEROMONE_PERCEPTION_THRESHOLD) {
                    registry.clear(it)
                }
            }
    }

    private fun maybeSpawnWalker(chance: Double) {
        val mostSuccessfulAnthill =
            registry.objects().filterIsInstance<Anthill>()
                .maxByOrNull { it.foodStored }
                ?: return

        val foodStored = mostSuccessfulAnthill.foodStored.toDouble()
        val n = Random.nextDouble()
        val foodFactor = (foodStored / FOOD_FOR_MAX_CHANCE).coerceIn(0.0, 1.0)
        val spawnChance = chance * foodFactor

        if (n < spawnChance) {
            registry.add(WalkerAnt.spawnAt(mostSuccessfulAnthill))
        }
    }
}
