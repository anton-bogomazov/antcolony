package com.abogomazov.antsim.domain

sealed class Object(val hex: Hex)

class Anthill(hex: Hex) : Object(hex)
class Food(hex: Hex, var amount: Int) : Object(hex)
class Obstacle(hex: Hex) : Object(hex)