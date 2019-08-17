package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.cpu.SourceMap
import dev.thecodewarrior.kotlincpu.computer.util.Ticker
import dev.thecodewarrior.kotlincpu.computer.util.dim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.slf4j.LoggerFactory
import java.awt.FlowLayout
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.io.File
import javax.swing.JFrame

class ComputerFrame(var program: File): JFrame(), WindowListener, CoroutineScope by CoroutineScope(Dispatchers.Main)  {
    var computer: Computer = Computer()

    val clock = Ticker("CPU Clock", 1.0) {
        step()
    }

    val cpuStatus = CpuStatusPanel(this)
    val sourceMapPanel = SourceMapPanel(this)

    init {
        title = "Computer"
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        layout = FlowLayout()

        add(cpuStatus)
        add(sourceMapPanel)

        size = preferredSize

        load()
    }

    fun step() {
        try {
            computer.step()
        } catch(e: Exception) {
            logger.error("Error stepping computer. Stopping clock")
            clock.stop()
        }
        cpuStatus.updateData()
        sourceMapPanel.updateData()
    }

    fun reset() {
        clock.stop()

        cpuStatus.reset()
        sourceMapPanel.reset()
        computer = Computer()
        load()

        clock.start()
    }

    fun load() {
        computer.memory.buffer.also { buf ->
            buf.rewind()
            buf.put(program.readBytes())
        }
        sourceMapPanel.sourceMap = SourceMap(program.resolveSibling(program.name + ".map"))
    }

    override fun windowClosed(e: WindowEvent?) {
        clock.stop()
    }
    override fun windowDeiconified(e: WindowEvent?) {}
    override fun windowClosing(e: WindowEvent?) {}
    override fun windowActivated(e: WindowEvent?) {}
    override fun windowDeactivated(e: WindowEvent?) {}
    override fun windowOpened(e: WindowEvent?) {}
    override fun windowIconified(e: WindowEvent?) {}

}

private val logger = LoggerFactory.getLogger(ComputerFrame::class.java)