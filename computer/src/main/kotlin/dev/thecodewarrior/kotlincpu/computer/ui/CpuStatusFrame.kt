package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.rem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.AbstractListModel
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.JToggleButton
import javax.swing.ListModel
import javax.swing.event.ListDataListener

class CpuStatusFrame(val cpu: CPU) : JFrame(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    init {
        title = "CPU"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = dim(650, 350)
        setLocationRelativeTo(null)
    }

    val powerButton = JToggleButton("Power") %
        { button ->
            button.addActionListener {
                cpu.computer.running = button.isSelected
            }
            contentPane.add(button, BorderLayout.PAGE_START)
        }

    val opcode = JLabel() %
        { label ->
            contentPane.add(label, BorderLayout.CENTER)
        }

    val registersModel = RegistersModel()
    val registersList = JList<String>() %
        { list ->
            list.model = registersModel
            list.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            val scrollPane = JScrollPane(list)
            contentPane.add(scrollPane, BorderLayout.LINE_END)
        }

    init {
        updateUI()
    }

    init {
        val postClock = cpu.computer.createPostClockChannel()
        launch {
            while(true) {
                postClock.receive()
                updateUI()
            }
        }
    }

    fun updateUI() {
        powerButton.isSelected = cpu.computer.running
        val insn = cpu.instructionAt(cpu.ctr.toInt())
        opcode.text = "next operation: 0x${insn.opcode.toString(16).padStart(4, '0')} ${insn.name}"
        registersModel.update()
    }

    inner class RegistersModel: AbstractListModel<String>() {
        override fun getElementAt(index: Int): String {
            val maxIndexWidth = (cpu.registers.count - 1).toString().length

            val indexText = index.toString().padStart(maxIndexWidth)
            val valueHex = cpu.registers.values[index].toString(16).padStart(16, '0')
            return "$indexText : 0x$valueHex"
        }

        override fun getSize(): Int {
            return cpu.registers.count
        }

        fun update() {
            fireContentsChanged(this, 0, cpu.registers.count)
        }
    }
}