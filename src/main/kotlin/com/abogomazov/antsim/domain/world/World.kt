package com.abogomazov.antsim.domain.world

import com.abogomazov.antsim.domain.hex.CubeCoordinate
import com.abogomazov.antsim.domain.hex.Direction
import com.abogomazov.antsim.domain.hex.Grid
import com.abogomazov.antsim.domain.hex.deltaTo
import com.abogomazov.antsim.domain.hex.toDirectionOrNull
import com.abogomazov.antsim.domain.hex.toVector
import com.abogomazov.antsim.domain.world.objects.Action
import com.abogomazov.antsim.domain.world.objects.Anthill
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.world.objects.Obstacle
import com.abogomazov.antsim.domain.world.objects.Walker
import com.abogomazov.antsim.domain.world.objects.WalkerAnt
import com.abogomazov.antsim.domain.world.objects.WorldObject
import com.abogomazov.antsim.domain.world.view.CellSense
import com.abogomazov.antsim.domain.world.view.LocalView
import kotlin.random.Random

val spawnChance: Double = 0.05
val evaporationRate: Double = 0.05

// TODO Refactor me. Concurrency, logs
class World(
    val grid: Grid,
    objects: Set<WorldObject>,
) {
    init {
        require(objects.any { it is Anthill }) {
            "The world should have at least one anthill"
        }
    }

    val objects: MutableSet<WorldObject> = objects.toMutableSet()
    private val objectMap: MutableMap<CubeCoordinate, WorldObject> =
        objects.associateBy { it.hex }.toMutableMap()

    val pheromones: MutableMap<CubeCoordinate, Double> = mutableMapOf()

    fun add(obj: WorldObject) {
        require(obj.hex in grid.cells)
        objectMap.computeIfAbsent(obj.hex) { obj }
        objects.add(obj)
    }

    fun clear(hex: CubeCoordinate) {
        val removed = objectMap.remove(hex)
        objects.remove(removed)
    }

    fun objectAt(hex: CubeCoordinate): WorldObject? =
        objectMap[hex]

    private fun isOccupied(hex: CubeCoordinate): Boolean =
        objectMap[hex] is Walker

    fun localViewFor(walker: Walker): LocalView =
        object : LocalView {

            override fun neighbors(): List<CellSense> =
                grid.neighborsOf(walker.hex).map { hex ->
                    val obj = objectAt(hex)
                    val relativeDirection =
                        checkNotNull(walker.hex.deltaTo(hex).toDirectionOrNull()) {
                            "Hex is not a neighbour"
                        }

                    CellSense(
                        relDirection = relativeDirection,
                        obstacle = obj is Obstacle,
                        food = (obj as? Food)?.amount ?: 0u,
                        pheromone = pheromones[hex] ?: 0.0,
                        anthill = obj is Anthill,
                        occupied = isOccupied(hex),
                        homeSignal = computeHomeSignal(hex)
                    )
                }
        }

    private fun computeHomeSignal(hex: CubeCoordinate): Double {
        val anthills = objects.filterIsInstance<Anthill>()
        return if (anthills.isEmpty()) 0.0
        else 1.0 / (anthills.minOf { it.hex.distance(hex) } + 1)
    }

    fun tick() {
        evaporatePheromones(evaporationRate)
        if (Random.nextDouble() <= spawnChance) {
            val anthills = objects.filterIsInstance<Anthill>()
            spawnWalker(anthills.random())
        }
        objects.filterIsInstance<Walker>()
            .associateWith { walker -> walker.decide(localViewFor(walker)) }
            .forEach { (walker, actions) ->
                println("Walker at ${walker.hex} decided to $actions")
                actions.forEach { apply(walker, it) }
            }
    }

    private fun apply(walker: Walker, action: Action) {
        when (action) {
            Action.Idle -> Unit
            is Action.Move -> moveWalker(walker, action.direction)
            Action.PickFood -> pickFood(walker)
            Action.DropFood -> dropFood(walker)
            is Action.DepositPheromone -> depositPheromone(walker.hex, action.amount)
        }
    }

    private fun moveWalker(walker: Walker, to: Direction) {
        val to = walker.hex + to.toVector()
        require(to in grid.cells)

        if (objectMap[to] != null) return

        objectMap.remove(walker.hex)
        walker.hex = to
        objectMap[to] = walker
    }

    private fun pickFood(walker: Walker) {
        val food = objectAt(walker.hex) as? Food ?: return

        if (food.amount == 0u) return

        walker.carryingFood = true
        food.take(1u)

        if (food.amount == 0u) {
            objectMap.remove(walker.hex)
            objects.remove(food)
        }
    }

    private fun dropFood(walker: Walker) {
        if (!walker.carryingFood) return

        objectAt(walker.hex) as? Anthill ?: return

        walker.carryingFood = false
    }

    fun depositPheromone(hex: CubeCoordinate, amount: Double) {
        pheromones[hex] = (pheromones[hex] ?: 0.0) + amount
    }

    fun evaporatePheromones(rate: Double) {
        pheromones.keys.toList().forEach { hex ->
            val value = (pheromones[hex] ?: 0.0) * (1.0 - rate)
            if (value < 0.001) pheromones.remove(hex)
            else pheromones[hex] = value
        }
    }

    fun spawnWalker(anthill: Anthill) {
        val ant = WalkerAnt.spawnAt(anthill)
        add(ant)
    }
}