package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.common.Condition
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.computer.Main
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import dev.thecodewarrior.kotlincpu.computer.util.extensions.rem
import dev.thecodewarrior.kotlincpu.computer.util.rem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.AbstractListModel
import javax.swing.BoxLayout
import javax.swing.GroupLayout
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
        size = dim(350, 350)
        setLocationRelativeTo(null)
    }

    val layout = GroupLayout(contentPane)
    init {
        contentPane.layout = layout
    }

    val resetButton = JButton("Reset") %
        { button ->
            button.addActionListener {
                Main.reset()
            }
            contentPane.add(button)
        }

    val stepButton = JButton("Step") %
        { button ->
            button.addActionListener {
                cpu?.computer?.step()
            }
            contentPane.add(button)
        }

    val clockModel = SpinnerNumberModel(1, 1, 1024, 1)
    val clockSpeed = JSpinner(clockModel) %
        { spinner ->
            spinner.addChangeListener {
                cpu?.computer?.clockSpeed = clockModel.number.toInt()
            }
            contentPane.add(spinner)
        }

    val clockButton = JToggleButton("Clock") %
        { button ->
            button.addActionListener {
                cpu?.computer?.running = button.isSelected
            }
            contentPane.add(button)
        }

    val instruction = JTextArea() %
        { label ->
            label.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            label.isEditable = false
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

        layout % {
            autoCreateGaps = true
            autoCreateContainerGaps = true

            horizontal = parallel {
                +sequential {
                    +resetButton
                    +stepButton
                    +clockSpeed
                    +clockButton
                }
                +sequential {
                    +instruction
                    +registersList
                }
            }
            vertical = sequential {
                +baseline {
                    +resetButton
                    +stepButton
                    +clockSpeed
                    +clockButton
                }
                +parallel {
                    +instruction
                    +registersList
                }
            }
        }

        updateUI()
    }

    fun updateUI() {
        val cpu = cpu
        if(cpu == null) {
            loadPlaceholderUI()
        } else {
            updateUI(cpu)
        }
    }

    private fun loadPlaceholderUI() {
        clockButton.isEnabled = false
        stepButton.isEnabled = false
        clockSpeed.isEnabled = false

        clockButton.isSelected = false
        this.instruction.text = """
                program counter: ???
                    opcode: ???
                    condition: ???
                    instruction: ???
                    payload: ???
            """.trimIndent()
        registersModel.update()
    }

    private fun updateUI(cpu: CPU) {
        clockButton.isEnabled = true
        stepButton.isEnabled = true
        clockSpeed.isEnabled = true

        clockButton.isSelected = cpu.computer.running

        updateInstructionInfo(cpu)

        registersModel.update()
    }

    private fun updateInstructionInfo(cpu: CPU) {
        val opcode = cpu.programBuffer.getUShort(cpu.pc.toInt())
        val insn = Insn.decode(opcode)
        val condition = Condition.decode(opcode)

        var addr = cpu.pc.toInt() + 2
        val payload = insn?.payload?.joinToString("") { arg ->
            val s = "\n        $arg: " +
                (0 until arg.type.width)
                    .map { i ->
                        cpu.programBuffer.get(addr + i).toString(16).padStart(2, '0')
                    }
                    .chunked(2)
                    .map { it.joinToString("") }
                    .joinToString(" ")
            addr += arg.type.width
            return@joinToString s
        } ?: "???"

        this.instruction.text = """
                program counter: 0x%s
                    opcode: 0x%s
                    condition: %s (%s)
                    instruction: %s
                    payload: %s
            """.trimIndent().format(
            cpu.pc.toString(16),
            opcode.toString(16).padStart(4, '0'),
            "$condition",
            if(condition.matches(cpu.flags.comparison)) "met" else "unmet",
            insn?.name ?: "???",
            payload
        )
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