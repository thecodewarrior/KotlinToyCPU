package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.common.Register
import dev.thecodewarrior.kotlincpu.common.Register.sp
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.rem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.exbin.deltahex.ViewMode
import org.exbin.deltahex.swing.CodeArea
import org.exbin.utils.binary_data.ByteArrayEditableData
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.AbstractListModel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane

class MemoryPanel(val frame: ComputerFrame): JPanel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    val ramArea = CodeArea()

    val stackModel = StackModel()
    val stackList = JList<String>() %
        { list ->
            list.model = stackModel
            list.font = Font(Font.MONOSPACED, Font.PLAIN, 14)
        }
    val stackScroll = JScrollPane(stackList)

    init {
        val layout = FlowLayout()
        this.layout = layout
        layout.hgap = 0
        layout.vgap = 0

        add(ramArea)
        add(stackScroll)

        preferredSize = dim(1200, 500)
        ramArea.preferredSize = dim(1000, 500)
        stackScroll.preferredSize = dim(200, 500)
    }

    fun reset() {
        ramArea.data = ByteArrayEditableData(frame.computer.memory.buffer.array())
    }

    fun updateData() {
        ramArea.repaint()
        stackModel.update()
    }

    inner class StackModel: AbstractListModel<String>() {
        val maxStack: Int
            get() = frame.computer.memory.size - 4

        override fun getElementAt(index: Int): String {
            val addressWidth = (frame.computer.memory.size - 1).toString(16).length
            val address = (maxStack - index * 4).toUInt()

            val valueHex = frame.computer.memory[address].toString(16).padStart(8, '0')
            val addressHex = address.toString(16).padStart(addressWidth)
            return "0x$addressHex : 0x$valueHex"
        }

        override fun getSize(): Int {
            return (maxStack - frame.computer.cpu.registers[sp].toInt()) / 4
        }

        fun update() {
            fireContentsChanged(this, 0, size)
        }
    }
}