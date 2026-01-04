package com.abogomazov.antsim

import com.abogomazov.antsim.app.AntsimApp
import com.abogomazov.antsim.app.config.Parameters
import com.abogomazov.antsim.parser.Config
import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    val configName = args.firstOrNull()
    val parameters =
        configName
            ?.let(::openConfig)
            ?.use { stream ->
                Config.parse(stream)
            } ?: Parameters.default

    AntsimApp(parameters).run()
}

private fun openConfig(name: String): InputStream? =
    File(name).takeIf { it.exists() }?.inputStream()
        ?: object {}.javaClass.getResourceAsStream("/$name")