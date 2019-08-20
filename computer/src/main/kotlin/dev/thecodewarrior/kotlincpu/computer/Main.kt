package dev.thecodewarrior.kotlincpu.computer

import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.ui.ComputerFrame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.prefs.Preferences
import javax.swing.UIManager

object Main : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    val preferences = Preferences.userNodeForPackage(Main::class.java)

    val computers = mutableListOf<ComputerFrame>()
}

fun main(args: Array<String>) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    Main // start app
    val frame = ComputerFrame(File(args[0]))
    frame.open()
    Main.computers.add(frame)
}


