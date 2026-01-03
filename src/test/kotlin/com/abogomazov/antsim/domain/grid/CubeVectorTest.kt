package com.abogomazov.antsim.domain.grid

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class CubeVectorTest : FunSpec({
    test("grid collinear vectors can be normalized") {
        checkAll(collinearCubeVector) { vector ->
            vector.normalizedOrNull().shouldNotBeNull()
        }
    }

    test("zero vector cannot be normalized") {
        CubeVector(0, 0, 0)
            .normalizedOrNull()
            .shouldBeNull()
    }

    test("vector can be calculated between 2 coordinates resulting in component deltas") {
        val a = CubeCoordinate(-7, 7, 0)
        val b = CubeCoordinate(7, -7, 0)

        CubeVector.between(a, b) shouldBe CubeVector(14, -14 ,0)
    }

    test("vector between neighbor coordinates can be normalized") {
        listOf(
            CubeCoordinate.ORIGIN to CubeCoordinate(-1, 1, 0),
            CubeCoordinate(-1, 2, -1) to CubeCoordinate(-1, 1, 0),
        ).forEach { (a, b) ->
            CubeVector.between(a, b).normalizedOrNull().shouldNotBeNull()
        }
    }

    test("collinear vectors maps into grid directions") {
        checkAll(collinearCubeVector) { vector ->
            vector.toDirectionOrNull().shouldNotBeNull()
        }
    }
})