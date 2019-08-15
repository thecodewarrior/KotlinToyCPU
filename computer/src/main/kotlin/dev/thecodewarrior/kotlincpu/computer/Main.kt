package dev.thecodewarrior.kotlincpu.computer

import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.ui.CpuStatusFrame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.prefs.Preferences
import javax.swing.UIManager

object Main : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    val preferences = Preferences.userNodeForPackage(Main::class.java)

    lateinit var program: File

    var computer = Computer()

    val cpuStatus = CpuStatusFrame()

    init {
        cpuStatus.isVisible = true
    }

    fun reset() {
        computer = Computer()

        computer.start()
        computer.memory.buffer.also { buf ->
            buf.rewind()
            buf.put(program.readBytes())
        }

        cpuStatus.cpu = computer.cpu
    }
}

fun main(args: Array<String>) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    Main // start app
    Main.program = File(args[0])
    Main.reset()
}


