package dev.thecodewarrior.kotlincpu.computer.util.extensions

import javax.swing.JComponent

inline operator fun <T: JComponent> T.rem(crossinline callback: (T) -> Unit): T {
    callback(this)
    return this
}