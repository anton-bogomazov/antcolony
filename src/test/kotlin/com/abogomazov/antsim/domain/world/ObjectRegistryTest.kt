package com.abogomazov.antsim.domain.world

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.domain.world.objects.Anthill
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.world.objects.WalkerAnt
import com.abogomazov.antsim.domain.world.objects.WorldObject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize

class ObjectRegistryTest : FunSpec({
    test("registry preserves all provided objects") {
        val grid = grid()
        val objects = listOf(
            Anthill(grid.cells.toList()[0], 0u),
            WalkerAnt(grid.cells.toList()[1], Direction.NW),
            Food(grid.cells.toList()[2], 2u),
        )

        val sut = ObjectRegistry(grid, objects.toSet())

        sut.objects() shouldContainExactly objects
    }

    test("a few objects can be placed in the same cell") {
        val grid = grid()
        val objects = listOf(
            WalkerAnt(grid.cells.toList()[0], Direction.NW),
            Food(grid.cells.toList()[0], 2u),
        )

        val sut = ObjectRegistry(grid, objects.toSet())

        sut.objects() shouldContainExactly objects
    }

    test("object can be removed from a cell") {
        val grid = grid()
        val objects = listOf(
            WalkerAnt(grid.cells.toList()[0], Direction.NW),
            Food(grid.cells.toList()[1], 2u),
        )

        val sut = ObjectRegistry(grid, objects.toSet())
        sut.clear(objects[0])

        sut.objectsAt(objects[0].hex).shouldBeEmpty()
        sut.objects().shouldHaveSize(1)
    }

    test("object can be moved to another cell") {
        val grid = grid()
        val objects = listOf(
            WalkerAnt(grid.cells.toList()[0], Direction.NW),
            Food(grid.cells.toList()[1], 2u),
        )

        val sut = ObjectRegistry(grid, objects.toSet())
        sut.move(objects[0], objects[1].hex)

        sut.objectsAt(objects[1].hex) shouldContainExactlyInAnyOrder objects
        sut.objectsAt(objects[0].hex).shouldBeEmpty()
    }
})

fun registry(
    grid: Grid = grid(),
    objects: Collection<WorldObject> = listOf(
        Anthill(grid.cells.toList()[0], 0u),
        WalkerAnt(grid.cells.toList()[6], Direction.NW),
        Food(grid.cells.toList()[4], 2u),
    )
): ObjectRegistry = ObjectRegistry(grid, objects.toSet())

fun grid(radius: UInt = 1u) = Grid(radius = radius)