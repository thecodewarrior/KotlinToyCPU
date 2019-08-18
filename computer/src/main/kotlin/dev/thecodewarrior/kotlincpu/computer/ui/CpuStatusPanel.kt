package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.common.Condition
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.common.util.getUShort
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.rem
import dev.thecodewarrior.kotlincpu.computer.util.rem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Font
import javax.swing.AbstractListModel
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSpinner
import javax.swing.JTextArea
import javax.swing.JToggleButton
import javax.swing.SpinnerNumberModel
import kotlin.math.roundToInt

class CpuStatusPanel(val frame: ComputerFrame): JPanel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    val cpu: CPU get() = frame.computer.cpu

    val layout = GroupLayout(this).also { setLayout(it) }

    val resetButton = JButton("Reset") %
        { button ->
            button.addActionListener {
                frame.reset()
            }
            this.add(button)
        }

    val stepButton = JButton("Step") %
        { button ->
            button.addActionListener {
                frame.step()
            }
            this.add(button)
        }

    val clockModel = SpinnerNumberModel(1, 1, 200_000_000, 1)
    val clockSpeed = JSpinner(clockModel) %
        { spinner ->
            spinner.addChangeListener {
                frame.clock.frequency = clockModel.number.toDouble()
            }
            this.add(spinner)
        }

    val clockButton = JToggleButton("Clock") %
        { button ->
            button.addActionListener {
                frame.clock.running = button.isSelected
            }
            this.add(button)
        }

    val actualFrequency = JLabel("") %
        { label ->
            label.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            this.add(label)
        }

    val instruction = JTextArea() %
        { area ->
            area.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            area.isEditable = false
            this.add(area)
        }

    val registersModel = RegistersModel()
    val registersList = JList<String>() %
        { list ->
            list.model = registersModel
            list.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            val scrollPane = JScrollPane(list)
            this.add(scrollPane)
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
                    +actualFrequency
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
                    +actualFrequency
                }
                +parallel {
                    +instruction
                    +registersList
                }
            }
        }

        updateData()

        preferredSize = dim(600, 350)
        minimumSize = preferredSize
    }

    fun reset() {

    }

    fun updateData() {
        clockButton.isEnabled = true
        stepButton.isEnabled = true
        clockSpeed.isEnabled = true

        clockButton.isSelected = frame.clock.running
        val frequency = frame.frequencyTracker.frequency
        if(frequency > 1000) {
            actualFrequency.text = "%3.2f kHz".format(frequency / 1000)
        } else {
            actualFrequency.text = "    %3d Hz".format(frequency.roundToInt())
        }

        updateInstructionInfo()

        registersModel.update()
    }

    private fun updateInstructionInfo() {
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
            val cpu = cpu
            val maxIndexWidth = (cpu.registers.count - 1).toString().length

            val indexText = index.toString().padStart(maxIndexWidth)
            val valueHex = cpu.registers[index].toString(16).padStart(8, '0')
            return "$indexText : 0x$valueHex"
        }

        override fun getSize(): Int {
            return cpu.registers.count
        }

        fun update() {
            fireContentsChanged(this, 0, size)
        }
    }
}