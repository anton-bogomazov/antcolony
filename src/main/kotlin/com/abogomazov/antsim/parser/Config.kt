package com.abogomazov.antsim.parser

import com.abogomazov.antsim.app.config.FrameSize
import com.abogomazov.antsim.app.config.Parameters
import com.abogomazov.antsim.app.config.WorldParameters
import java.io.InputStream
import kotlin.time.Duration.Companion.milliseconds

class Config(
    val header: ConfigHeader,
    val defs: List<WorldObjectDefinition>
) {
    companion object {
        fun parse(inputStream: InputStream): Parameters {
            val lines = inputStream.bufferedReader()
                .readLines()
                .map { it.trim() }
                .filter { it.isNotEmpty() && !it.startsWith("#") }

            return parse(lines).toParameters()
        }

        private fun parse(lines: List<String>): Config =
            Config(
                header = ConfigHeader.parse(lines.take(4)),
                defs = lines.drop(4).map { WorldObjectDefinition.parse(it) }
            )
    }

    fun toParameters() =
        Parameters(
            gridRadius = header.radius.toUInt(),
            frameSize = FrameSize(
                header.frameSize.first,
                header.frameSize.second
            ),
            tickRate = header.tickRate.milliseconds,
            world = WorldParameters(
                initialObjects = defs.map { it.toDomain() }.toSet(),
                spawnChanceModifier = header.spawnRate,
                evaporationRate = header.evaporationRate,
            )
        )
}