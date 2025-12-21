package com.abogomazov.antsim.parser

import com.abogomazov.antsim.domain.Anthill
import com.abogomazov.antsim.domain.Food
import com.abogomazov.antsim.domain.Hex
import com.abogomazov.antsim.domain.Obstacle
import com.abogomazov.antsim.domain.WorldObject
import kotlin.time.Duration

data class SimulationParameters(
    val worldSize: UInt,
    val tickRate: Duration,
//    val spawnRate: Double,
    val objects: List<WorldObjectDefinition>
)

data class AxialCoordinate(
    val r: Int,
    val q: Int,
) {
    // FIXME should I introduce Cube Coordinate?
    fun toCube(): Hex {
        val x = q
        val z = r
        val y = -x - z
        return Hex(x, y, z)
    }
}

// TODO make polymorphic
data class WorldObjectDefinition(
    val coordinate: AxialCoordinate,
    val type: WorldObjectType,
    // required if food
    val amount: UInt? = null,
) {
    init {
        if (type == WorldObjectType.FOOD) {
            requireNotNull(amount)
        } else {
            require(amount == null)
        }
    }
}

enum class WorldObjectType {
    FOOD,
    ANTHILL,
    OBSTACLE,
}

fun WorldObjectDefinition.toDomain(): WorldObject =
    when (type) {
        WorldObjectType.FOOD -> Food(coordinate.toCube(), amount!!)
        WorldObjectType.ANTHILL -> Anthill(coordinate.toCube())
        WorldObjectType.OBSTACLE -> Obstacle(coordinate.toCube())
    }
