package dev.thecodewarrior.kotlincpu.computer

import dev.thecodewarrior.kotlincpu.computer.ui.CpuStatusController
import dev.thecodewarrior.kotlincpu.computer.util.resource
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.nio.ByteBuffer
import java.util.prefs.Preferences

class Main: Application() {
    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        Companion.primaryStage = primaryStage
        val loader = FXMLLoader()
        val root = loader.load<Parent>(resource("/ui/CpuStatusController.fxml").openStream())
        val controller = loader.getController<CpuStatusController>()
        primaryStage.title = "CPU"
        primaryStage.scene = Scene(root, 320.0, 270.0)
        primaryStage.show()

        val programFile = File(parameters.named["program"]!!)
        val program = ByteBuffer.wrap(programFile.readBytes())
        controller.computer.loadProgram(program)
        controller.computer.running = true
        GlobalScope.launch {
            controller.computer.start()
        }
    }

    companion object {
        lateinit var primaryStage: Stage
        val preferences = Preferences.userNodeForPackage(Main::class.java)
    }
}

fun main(args: Array<String>) {
//    SoundEffects // load the converter sound up front to avoid obtuse errors
    Application.launch(Main::class.java, *args)
}
