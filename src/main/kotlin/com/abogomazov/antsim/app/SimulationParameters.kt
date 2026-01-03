package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.world.objects.Anthill
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.hex.CubeCoordinate
import com.abogomazov.antsim.domain.world.objects.Obstacle
import com.abogomazov.antsim.domain.world.objects.WorldObject
import kotlin.time.Duration

data class SimulationParameters(
    val worldSize: UInt,
    val tickRate: Duration,
    val objects: List<WorldObjectDefinition>
)

data class AxialCoordinate(
    val r: Int,
    val q: Int,
) {
    // FIXME should I introduce Cube Coordinate?
    fun toCube(): CubeCoordinate {
        val x = q
        val z = r
        val y = -x - z
        return CubeCoordinate(x, y, z)
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
