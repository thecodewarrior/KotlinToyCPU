package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.common.Register
import dev.thecodewarrior.kotlincpu.common.Register.*
import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.cpu.Peripheral
import dev.thecodewarrior.kotlincpu.computer.util.KiB
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
import java.awt.event.WindowListener
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextPane
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.SimpleAttributeSet

class LogDisplayFrame(override var computer: Computer): JFrame(), Peripheral, CoroutineScope by CoroutineScope(Dispatchers.Main) {
    val output = Channel<Char>(64 * KiB)

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
            for(character in output) {
                logDocument.insertString(logDocument.length, "$character", SimpleAttributeSet.EMPTY)
            }
        }
    }

    fun reset() {
        logDocument.remove(0, logDocument.length)
    }

    fun updateData() {

    }

    override fun call() {
        val command = computer.cpu.registers[r0]
        when(command) {
            0u -> output.offer(computer.cpu.registers[r1].toInt().toChar())
        }
    }

    override fun step() {

    }
}