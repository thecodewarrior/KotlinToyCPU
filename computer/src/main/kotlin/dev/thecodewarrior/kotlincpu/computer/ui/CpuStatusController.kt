package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import dev.thecodewarrior.kotlincpu.computer.cpu.Registers
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.text.Font
import javafx.util.Callback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class CpuStatusController {
    val computer = Computer()

    val registersList = FXCollections.observableArrayList((0 until computer.cpu.registers.count).toList())

    @FXML
    lateinit var registersListView: ListView<Int>
    val registerCells = mutableListOf<WeakReference<RegisterCell>>()

    fun initialize() {
        val weakThis = WeakReference(this)
        val postClock = computer.createPostClockChannel()

        GlobalScope.launch(Dispatchers.JavaFx) {
            val controller = weakThis.get()
            if(controller != null && postClock.poll() != null) {
                controller.registerCells.forEach {
                    it.get()?.updateValue()
                }
            }
        }

        registersListView.items = registersList
        registersListView.cellFactory = Callback {
            RegisterCell(computer.cpu.registers).also {
                registerCells.add(WeakReference(it))
            }
        }
    }

    class RegisterCell(val registers: Registers): ListCell<Int>() {
        val registerDigits = (registers.count - 1).toString().length
        var registerIndex = 0

        init {
            this.font = Font.font("Monospaced")
        }

        override fun updateItem(item: Int?, empty: Boolean) {
            super.updateItem(item, empty)
            if(item == null) {
                registerIndex = -1
            } else {
                registerIndex = item
            }
            updateValue()
        }

        fun updateValue() {
            if(registerIndex < 0) {
                text = ""
            } else {
                val index = registerIndex.toString().padStart(registerDigits, '0')
                val value = registers.values[registerIndex].toString(16).padStart(16, '0')
                text = "R$index: 0x$value"
            }
        }
    }
}