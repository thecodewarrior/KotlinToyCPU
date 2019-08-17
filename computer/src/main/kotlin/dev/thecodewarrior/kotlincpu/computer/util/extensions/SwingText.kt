package dev.thecodewarrior.kotlincpu.computer.util.extensions

import com.sun.codemodel.internal.JExpr.component
import java.awt.Font.PLAIN
import javax.swing.text.StyleConstants
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.getAttributes
import dev.thecodewarrior.kotlincpu.computer.util.vec
import dev.thecodewarrior.kotlincpu.computer.util.veci
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Rectangle
import java.util.HashMap
import javax.swing.Spring.height
import javax.swing.text.BadLocationException
import javax.swing.text.JTextComponent
import kotlin.math.max
import kotlin.math.min

fun JTextComponent.lineBounds(line: Int): Rectangle? {
    if(line < 0 || this.document.defaultRootElement.elementCount <= line)
        return null
    val element = this.document.defaultRootElement.getElement(line)
    val startRect = this.modelToView(element.startOffset) ?: return null
    val endRect = this.modelToView(element.endOffset) ?: return null
    val min = veci(min(startRect.x, endRect.x), min(startRect.y, endRect.y))
    val max = veci(
        max(startRect.x + startRect.width, endRect.x + endRect.width),
        max(startRect.y + startRect.height, endRect.y + endRect.height)
    )
    val size = max - min
    return Rectangle(min.x, min.y, size.x, size.y)
}

// source: https://tips4java.wordpress.com/2009/05/23/text-component-line-number/
/**
 *  Determine the Y offset for the current row
 */
@Throws(BadLocationException::class)
private fun JTextComponent.getOffsetY(rowStartOffset: Int): Int {

    val fontMetrics = this.getFontMetrics(this.font)
    //  Get the bounding rectangle of the row

    val r = this.modelToView(rowStartOffset)
    val lineHeight = fontMetrics.height
    val y = r.y + r.height
    var descent = 0

    //  The text needs to be positioned above the bottom of the bounding
    //  rectangle based on the descent of the font(s) contained on the row.

    if (r.height == lineHeight) {
        // default font is being used
        descent = fontMetrics.descent
    } else {
        // We need to check all the attributes for font changes
        val root = this.document.defaultRootElement
        val index = root.getElementIndex(rowStartOffset)
        val line = root.getElement(index)

        for (i in 0 until line.elementCount) {
            val child = line.getElement(i)
            val attributes = child.attributes
            val fontFamily = attributes as String
            val fontSize = attributes as Int
            val key = fontFamily + fontSize

            val font = Font(fontFamily, PLAIN, fontSize)
            val fm = this.getFontMetrics(font)

            descent = max(descent, fm.descent)
        }
    }

    return y - descent
}
