package com.abogomazov.antsim

import com.abogomazov.antsim.app.AntsimApp
import com.abogomazov.antsim.parser.Config

fun main() {
    val config = object {}.javaClass.getResourceAsStream("/small-world.txt")!!
    val parameters = Config.parse(config)
    AntsimApp(parameters).run()
}