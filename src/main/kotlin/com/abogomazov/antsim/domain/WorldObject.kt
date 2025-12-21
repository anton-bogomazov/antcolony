package com.abogomazov.antsim.domain

sealed class WorldObject(val hex: Hex)

class Anthill(hex: Hex) : WorldObject(hex)
class Food(hex: Hex, var amount: UInt) : WorldObject(hex)
class Obstacle(hex: Hex) : WorldObject(hex)