package com.abogomazov.antsim.app.config.parser

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.world.objects.Anthill
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.world.objects.Obstacle
import com.abogomazov.antsim.domain.world.objects.WalkerAnt
import com.abogomazov.antsim.domain.world.objects.WorldObject

sealed interface WorldObjectDefinition {
    companion object {
        fun parse(line: String): WorldObjectDefinition {
            val parts = line.split(",").map { it.trim() }
            val type = WorldObjectType.valueOf(parts[0].uppercase())
            val q = parts.getOrNull(1)?.toInt()
                ?: error("Missing q coordinate for object $type")
            val r = parts.getOrNull(2)?.toInt()
                ?: error("Missing r coordinate for object $type")
            val coordinate = AxialCoordinate(q, r)

            return when (type) {
                WorldObjectType.FOOD -> FoodDefinition(coordinate, parts.getOrNull(3)?.toInt()!!)
                WorldObjectType.ANTHILL -> AnthillDefinition(coordinate, parts.getOrNull(3)?.toInt()!!)
                WorldObjectType.OBSTACLE -> ObstacleDefinition(coordinate)
                WorldObjectType.ANT -> AntDefinition(coordinate, parts.getOrNull(3)!!.let { Direction.valueOf(it.uppercase()) })
            }
        }
    }
}

enum class WorldObjectType {
    FOOD,
    ANTHILL,
    OBSTACLE,
    ANT,
}

data class AnthillDefinition(val coordinate: AxialCoordinate, val amount: Int) : WorldObjectDefinition
data class FoodDefinition(val coordinate: AxialCoordinate, val amount: Int) : WorldObjectDefinition
data class ObstacleDefinition(val coordinate: AxialCoordinate) : WorldObjectDefinition
data class AntDefinition(val coordinate: AxialCoordinate, val orientation: Direction) : WorldObjectDefinition

fun WorldObjectDefinition.toDomain(): WorldObject =
    when (this) {
        is FoodDefinition -> Food(coordinate.toCube(), amount.toUInt())
        is AnthillDefinition -> Anthill(coordinate.toCube(), amount.toUInt())
        is ObstacleDefinition -> Obstacle(coordinate.toCube())
        is AntDefinition -> WalkerAnt(coordinate.toCube(), orientation)
    }