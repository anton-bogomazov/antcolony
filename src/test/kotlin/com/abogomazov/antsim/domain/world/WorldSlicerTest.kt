package com.abogomazov.antsim.domain.world

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.world.objects.WalkerAnt
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class WorldSlicerTest : FunSpec({
    test("sense is an information about observable by walker cells") {
        val grid = grid()
        val walker = WalkerAnt(grid.cells.toList()[0], Direction.NW)
        val registry = registry(grid, listOf(
            walker,
            Food(grid.cells.toList()[1], 2u),
        ))
        val sut = WorldSlicer(grid, registry)

        val view = sut.localViewFor(walker)

        view.neighbors().shouldHaveSize(3) // walker is in the corner
            .single { it.food > 0u }
            .relDirection shouldBe Direction.NW // and senses food on the neighbor cell
    }
})