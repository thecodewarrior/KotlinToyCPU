package dev.thecodewarrior.kotlincpu.computer.util

import dev.thecodewarrior.kotlincpu.computer.Main

fun resource(name: String) = Main::class.java.getResource(name)
fun resourceStream(name: String) = Main::class.java.getResourceAsStream(name)
