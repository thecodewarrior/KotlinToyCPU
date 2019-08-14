package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.rem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.AbstractListModel
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSpinner
import javax.swing.JToggleButton
import javax.swing.SpinnerNumberModel

class CpuStatusFrame(val cpu: CPU) : JFrame(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    init {
        title = "CPU"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = dim(650, 350)
        setLocationRelativeTo(null)
    }

    val topRow = JPanel() %
        { row ->
            row.layout = BoxLayout(row, BoxLayout.LINE_AXIS)
            contentPane.add(row, BorderLayout.PAGE_START)
        }
    val stepButton = JButton("Step") %
        { button ->
            button.addActionListener {
                cpu.computer.step()
            }
            topRow.add(button)
        }

    val clockModel = SpinnerNumberModel(cpu.computer.clockSpeed, 1, 32, 1)
    val clockSpeed = JSpinner(clockModel) %
        { spinner ->
            spinner.addChangeListener {
                cpu.computer.clockSpeed = clockModel.number.toInt()
            }
            topRow.add(spinner)
        }

    val powerButton = JToggleButton("Clock") %
        { button ->
            button.addActionListener {
                cpu.computer.running = button.isSelected
            }
            topRow.add(button)
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
        val insn = cpu.instructionAt(cpu.pc.toInt())
        opcode.text = "next operation: 0x${insn.opcode.opcode.toString(16).padStart(4, '0')} ${insn.name}"
        registersModel.update()
    }

    inner class RegistersModel: AbstractListModel<String>() {
        override fun getElementAt(index: Int): String {
            val maxIndexWidth = (cpu.registers.count - 1).toString().length

            val indexText = index.toString().padStart(maxIndexWidth)
            val valueHex = cpu.registers[index].toString(16).padStart(8, '0')
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