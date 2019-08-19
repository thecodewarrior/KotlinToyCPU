package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.util.dim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.exbin.deltahex.ViewMode
import org.exbin.deltahex.swing.CodeArea
import org.exbin.utils.binary_data.ByteArrayEditableData
import javax.swing.JPanel

class MemoryPanel(val frame: ComputerFrame): JPanel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    val ramArea = CodeArea()

    init {
        add(ramArea)
        preferredSize = dim(1200, 500)
        ramArea.preferredSize = dim(1200, 500)
        ramArea.viewMode = ViewMode.CODE_MATRIX
        ramArea.byteGroupSize = 4
    }

    fun reset() {
        ramArea.data = ByteArrayEditableData(frame.computer.memory.buffer.array())
    }

    fun updateData() {
        ramArea.repaint()
//        ramArea.notifyDataChanged() // we know the data hasn't structurally changed, so we don't need to recompute anything
    }
}