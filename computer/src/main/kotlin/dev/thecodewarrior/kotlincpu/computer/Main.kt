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

    var computer = Computer()

    val cpuStatus = CpuStatusFrame(computer.cpu)

    init {
        launch {
            computer.start()
        }
        cpuStatus.isVisible = true
    }

}

fun main(args: Array<String>) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    Main // start app
    Main.computer.memory.buffer.also { buf ->
        buf.rewind()
        buf.put(File(args[0]).readBytes())
    }
}


