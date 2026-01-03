package com.abogomazov.antsim.domain.grid

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class CubeCoordinateTest : FunSpec({
    test("valid cube coordinate has a zero dimension sum") {
        checkAll(validCubeCoordinateComponents) { (x, y, z) ->
            CubeCoordinate(x, y, z)
        }
    }

    test("cannot construct cube coordinate from components which does not sum to zero") {
        checkAll(invalidCubeCoordinateComponents) { (x, y, z) ->
            shouldThrow<IllegalArgumentException> {
                CubeCoordinate(x, y, z)
            }
        }
    }

    test("sum of coordinate and vector results in a new coordinate with the sum of components") {
        checkAll(validCubeCoordinate, validCubeVector) { a, b ->
            a + b shouldBe CubeCoordinate(
                x = a.x + b.dx,
                y = a.y + b.dy,
                z = a.z + b.dz,
            )
        }
    }

    test("difference of coordinate and vector results in a new coordinate with the difference of components") {
        checkAll(validCubeCoordinate, validCubeVector) { a, b ->
            a - b shouldBe CubeCoordinate(
                x = a.x - b.dx,
                y = a.y - b.dy,
                z = a.z - b.dz,
            )
        }
    }

    test("distance between cube coordinates is a max abs component delta") {
        val c = CubeCoordinate(-4, 3, 1)

        c.distance(CubeCoordinate.ORIGIN) shouldBe
                CubeCoordinate.ORIGIN.distance(c) shouldBe 4
    }
})

