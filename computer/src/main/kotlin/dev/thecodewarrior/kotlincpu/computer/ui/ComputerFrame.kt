package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.cpu.SourceMap
import dev.thecodewarrior.kotlincpu.computer.util.FrequencyTracker
import dev.thecodewarrior.kotlincpu.computer.util.Ticker
import dev.thecodewarrior.kotlincpu.computer.util.dim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.awt.FlowLayout
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.io.File
import javax.swing.JFrame
import org.exbin.utils.binary_data.ByteArrayEditableData
import org.exbin.deltahex.swing.CodeArea



class ComputerFrame(var program: File): JFrame(), WindowListener, CoroutineScope by CoroutineScope(Dispatchers.Main)  {
    var computer: Computer = Computer()

    val clock = Ticker("CPU Clock", 1.0) {
        step()
    }
    var frequencyTracker = FrequencyTracker(50, 200)

    val cpuStatus = CpuStatusPanel(this)
    val sourceMapPanel = SourceMapPanel(this)
    val updateChannel = Channel<Unit>()
    val memoryPanel = MemoryPanel(this)

    init {
        title = "Computer"
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        val layout = FlowLayout()
        this.layout = layout
        layout.hgap = 0
        layout.vgap = 0

        add(cpuStatus)
        add(sourceMapPanel)
        add(memoryPanel)

        size = dim(1200, 850)

        reset()

        launch {
            for(unused in updateChannel) {
                cpuStatus.updateData()
                sourceMapPanel.updateData()
                memoryPanel.updateData()
            }
        }
    }

    fun step() {
        try {
            computer.step()
        } catch(e: Exception) {
            logger.error("Error stepping computer. Stopping clock")
            clock.stop()
        }
        frequencyTracker.tick()
        updateData()
    }

    fun updateData() {
        updateChannel.offer(Unit)
    }

    fun reset() {
        clock.stop()

        computer = Computer()
        cpuStatus.reset()
        sourceMapPanel.reset()
        memoryPanel.reset()

        load()
        updateData()
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