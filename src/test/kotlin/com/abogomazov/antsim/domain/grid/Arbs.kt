package com.abogomazov.antsim.domain.grid

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.merge
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.arbitrary.positiveInt

val collinearCubeVector = arbitrary {
    val component =
        Arb.int(Int.MIN_VALUE + 1, Int.MAX_VALUE)
            .filter { it != 0 }
            .bind()
    val components = listOf(
        component,
        -component,
        0
    )
    val (x, y, z) = components.shuffled()
    CubeVector(x, y, z)
}

val validCubeVector = arbitrary {
    val (x, y, z) = validCubeCoordinateComponents.bind()
    CubeVector(x, y, z)
}

val validCubeCoordinate = arbitrary {
    val (x, y, z) = validCubeCoordinateComponents.bind()
    CubeCoordinate(x, y, z)
}

val validCubeCoordinateComponents = arbitrary {
    val x = Arb.int().bind()
    val y = Arb.int().bind()
    val z = -x - y

    Triple(x, y, z)
}

val invalidCubeCoordinateComponents = arbitrary {
    val x = Arb.int().bind()
    val y = Arb.int().bind()
    val z = Arb.int().bind()

    if (x + y + z == 0) {
        val shift = if (Arb.boolean().bind()) 1 else -1
        Triple(x, y, z + shift)
    } else {
        Triple(x, y, z)
    }
}