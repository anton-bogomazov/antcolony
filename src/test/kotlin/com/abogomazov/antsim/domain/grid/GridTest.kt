package com.abogomazov.antsim.domain.grid

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.uInt
import io.kotest.property.checkAll

class GridTest : FunSpec({
    test("grid has 1+3r(r+1) cells - 6r per layer") {
        Arb.uInt(min = 1u, max = 100u).checkAll { r ->
            val expected = 1 + 3*r.toInt()*(r.toInt()+1)
            Grid(r).cells.size shouldBe expected
        }
    }

    test("cannot construct a huge grid") {
        Arb.uInt(min = 101u).checkAll { r ->
            shouldThrow<IllegalArgumentException> {
                Grid(r)
            }
        }
    }

    test("cannot construct a grid with zero radius") {
        shouldThrow<IllegalArgumentException> {
            Grid(0u)
        }
    }

    test("smallest grid has 7 cells") {
        Grid(1u).cells shouldBe setOf(
            CubeCoordinate(1, -1, 0),
            CubeCoordinate(1, 0, -1),
            CubeCoordinate(0, 1, -1),
            CubeCoordinate(-1, 1, 0),
            CubeCoordinate(-1, 0, 1),
            CubeCoordinate(0, -1, 1),
            CubeCoordinate(0, 0, 0),
        )
    }
})