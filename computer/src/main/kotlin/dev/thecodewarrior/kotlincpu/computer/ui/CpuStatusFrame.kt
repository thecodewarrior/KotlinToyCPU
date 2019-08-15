package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.common.Condition
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
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
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSpinner
import javax.swing.JTextArea
import javax.swing.JToggleButton
import javax.swing.SpinnerNumberModel

class CpuStatusFrame : JFrame(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    var cpu: CPU? = null
        set(value) {
            field = value

            val postClock = cpu?.computer?.createUpdateChannel()
            launch {
                while(true) {
                    postClock?.receive()
                    updateUI()
                }
            }

            updateUI()
        }

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
                cpu?.computer?.step()
            }
            topRow.add(button)
        }

    val clockModel = SpinnerNumberModel(1, 1, 32, 1)
    val clockSpeed = JSpinner(clockModel) %
        { spinner ->
            spinner.addChangeListener {
                cpu?.computer?.clockSpeed = clockModel.number.toInt()
            }
            topRow.add(spinner)
        }

    val powerButton = JToggleButton("Clock") %
        { button ->
            button.addActionListener {
                cpu?.computer?.running = button.isSelected
            }
            topRow.add(button)
        }

    val instruction = JTextArea() %
        { label ->
            label.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
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
    }

    fun updateUI() {
        val cpu = cpu
        if(cpu == null) {
            powerButton.isSelected = false
            this.instruction.text = """
                program counter: ???
                    opcode: ???
                    condition: ???
                    instruction: ???
                    payload: ???
            """.trimIndent()
            registersModel.update()
        } else {
            powerButton.isSelected = cpu.computer.running

            val opcode = cpu.programBuffer.getUShort(cpu.pc.toInt())
            val insn = Insn.decode(opcode)
            val condition = Condition.decode(opcode)

            var addr = cpu.pc.toInt() + 2
            val payload = insn?.payload?.joinToString("") { arg ->
                val s = "\n        $arg: " +
                    (0 until arg.width)
                        .map { i ->
                            cpu.programBuffer.get(addr + i).toString(16).padStart(2, '0')
                        }
                        .chunked(2)
                        .map { it.joinToString("") }
                        .joinToString(" ")
                addr += arg.width
                return@joinToString s
            } ?: "???"

            this.instruction.text = """
                program counter: 0x%s
                    opcode: 0x%s
                    condition: %s
                    instruction: %s
                    payload: %s
            """.trimIndent().format(
                cpu.pc.toString(16),
                opcode.toString(16).padStart(4, '0'),
                "$condition",
                insn?.name ?: "???",
                payload
            )

            registersModel.update()
        }
    }

    inner class RegistersModel: AbstractListModel<String>() {
        override fun getElementAt(index: Int): String {
            val cpu = cpu ?: return ""
            val maxIndexWidth = (cpu.registers.count - 1).toString().length

            val indexText = index.toString().padStart(maxIndexWidth)
            val valueHex = cpu.registers[index].toString(16).padStart(8, '0')
            return "$indexText : 0x$valueHex"
        }

        override fun getSize(): Int {
            return cpu?.registers?.count ?: 0
        }

        fun update() {
            fireContentsChanged(this, 0, size)
        }
    }
}