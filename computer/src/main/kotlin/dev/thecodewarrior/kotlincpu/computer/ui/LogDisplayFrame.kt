package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.common.DataSize
import dev.thecodewarrior.kotlincpu.common.Register
import dev.thecodewarrior.kotlincpu.common.Register.*
import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.cpu.Peripheral
import dev.thecodewarrior.kotlincpu.computer.util.KiB
import dev.thecodewarrior.kotlincpu.computer.util.StringFormat
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.rem
import dev.thecodewarrior.kotlincpu.computer.util.resource
import dev.thecodewarrior.kotlincpu.computer.util.vec
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.Font
import java.awt.Rectangle
import java.awt.event.WindowListener
import java.nio.ByteBuffer
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextPane
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.SimpleAttributeSet

class LogDisplayFrame(override var computer: Computer): JFrame(), Peripheral, CoroutineScope by CoroutineScope(Dispatchers.Main) {
    val output = Channel<String>(64 * KiB)

    val logDocument = DefaultStyledDocument()
    val log = JTextPane() %
        { log ->
            log.document = logDocument
            log.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
        }
    val scroll = JScrollPane(log)

    init {
        size = dim(800, 250)
        add(scroll)

        launch {
            for(string in output) {
                val atBottom = log.bounds.maxY.toInt() == scroll.viewport.height
                logDocument.insertString(logDocument.length, string, SimpleAttributeSet.EMPTY)
                if(atBottom) {
                    scroll.revalidate()
                    val sb = scroll.verticalScrollBar
                    sb.value = sb.maximum
                }
            }
        }
    }

    fun reset() {
        logDocument.remove(0, logDocument.length)
    }

    fun updateData() {

    }

    override fun call(function: UInt) {
        when(function) {
            0u -> output.offer("${computer.cpu.registers[r0].toInt().toChar()}")
            1u -> {
                output.offer(computer.memory.buffer.getAsciiz(computer.cpu.registers[r0].toInt()))
            }
            2u -> {
                val formatString = computer.memory.buffer.getAsciiz(computer.cpu.registers[r0].toInt())
                var formatted = formatString

                val placeholders = formatPattern.findAll(formatString).toList().reversed()
                if(placeholders.isNotEmpty()) {
                    val parameterCount = if(placeholders.all { it.groups[1] == null })
                        placeholders.size
                    else
                        placeholders.asSequence().map { it.groups[1]?.value?.toInt() ?: 0 }.max() ?: 0

                    val stack = computer.cpu.registers[sp]
                    val stackArguments = (1 .. parameterCount).reversed().map { i ->
                        computer.memory[stack + i.toUInt() * 4u]
                    }
                    computer.cpu.registers[sp] = stack + parameterCount.toUInt() * 4u

                    val arguments: Array<Any> = (stackArguments as List<Any>).toTypedArray()
                    placeholders.forEachIndexed { i, match ->
                        val (parameter, flags, width, precision, length, type) = match.destructured
                        if(type == "s") {
                            val parameterIndex = if(parameter == "") i else parameter.toInt()
                            arguments[parameterIndex] = computer.memory.buffer.getAsciiz(stackArguments[parameterIndex].toInt())
                        }
                    }

                    formatted = formatString.format(*arguments)
                }

                output.offer(formatted)
            }
        }
    }

    private fun ByteBuffer.getAsciiz(address: Int): String {
        var len = 0
        while(this.get(address + len) != 0.toByte()) len++
        val bytes = ByteArray(len) {
            this.get(address + it)
        }
        return String(bytes)
    }

    override fun step() {

    }

    // based on: https://en.wikipedia.org/wiki/Printf_format_string#Format_placeholder_specification
    // val (parameter, flags, width, precision, length, type) = match.destructured
    val formatPattern = """%(?:(\d+)\$)?([-+ 0#]*)(\*|\d+)?(?:\.(\*|\d+))?(hh|h|ll|l|L|z|j|t)?([%diufFeEgGxXoscpaAn])""".toRegex()
}