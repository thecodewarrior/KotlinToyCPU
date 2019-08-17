package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.cpu.Location
import dev.thecodewarrior.kotlincpu.computer.cpu.SourceMap
import dev.thecodewarrior.kotlincpu.computer.util.dim
import dev.thecodewarrior.kotlincpu.computer.util.extensions.lineBounds
import dev.thecodewarrior.kotlincpu.computer.util.extensions.rem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.awt.BorderLayout
import java.awt.Font
import java.awt.Point
import java.awt.Rectangle
import javax.swing.AbstractListModel
import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextPane
import javax.swing.ListModel
import javax.swing.text.AttributeSet
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.Document
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.Utilities

class SourceMapPanel(val frame: ComputerFrame): JPanel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    val cpu: CPU get() = frame.computer.cpu

    var sourceMap: SourceMap = SourceMap()
        set(value) {
            field = value
            currentLocation = null
            updateData()
        }
    var currentLocation: Location? = null

    val sourceArea = JTextPane() %
        { area ->
            area.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            area.isEditable = false
        }
    val sourceScroll = JScrollPane(sourceArea) %
        { scroll ->
            val lineNumbers = TextLineNumber(sourceArea)
//            lineNumbers.currentLineForeground = null
            scroll.setRowHeaderView(lineNumbers)
        }

    val files = mutableMapOf<String, Document>()
    val emptyDocument = DefaultStyledDocument().also {
        it.insertString(0, "???", SimpleAttributeSet.EMPTY)
    }

    init {
        this.add(sourceScroll)
        preferredSize = dim(600, 350)
        minimumSize = preferredSize

        sourceScroll.preferredSize = preferredSize
    }

    fun reset() {
        files.clear()
    }

    fun updateData() {
        val newLocation = sourceMap[cpu.pc.toInt()]
        if(newLocation != currentLocation) {

            if(newLocation?.file != currentLocation?.file) {
                val file = newLocation?.file
                if(file == null) {
                    sourceArea.document = emptyDocument
                } else {
                    sourceArea.document = files.getOrPut(file) {
                        DefaultStyledDocument().also { doc ->
                            doc.insertString(0, sourceMap.root.resolve(file).readText(), SimpleAttributeSet.EMPTY)
                        }
                    }
                }
            }

            currentLocation = newLocation

            if(newLocation != null) {
                val lineRect = sourceArea.lineBounds(newLocation.line)
                if(lineRect != null) {
                    val lineElement = sourceArea.document.defaultRootElement.getElement(newLocation.line)
                    sourceArea.caretPosition = lineElement.startOffset

                    val viewBounds = sourceScroll.bounds
                    val centeredRect = Rectangle(
                        0,
                        lineRect.y - (viewBounds.height - lineRect.height) / 2,
                        viewBounds.width,
                        viewBounds.height
                    )

                    sourceArea.scrollRectToVisible(centeredRect)
                }
            }
        }
    }
}