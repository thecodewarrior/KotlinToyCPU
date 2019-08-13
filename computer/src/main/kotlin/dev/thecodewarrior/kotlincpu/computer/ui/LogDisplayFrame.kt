package dev.thecodewarrior.kotlincpu.computer.ui

import dev.thecodewarrior.kotlincpu.computer.util.resource
import dev.thecodewarrior.kotlincpu.computer.util.vec
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage

class LogDisplayFrame {
    companion object {
        fun open() {
            val root = FXMLLoader.load<Parent>(resource("/ui/settings.fxml"))
            val stageSize = vec(250.0, 250.0)
            val stage = Stage()
//            stage.initModality(Modality.APPLICATION_MODAL)
//            stage.initOwner(Main.primaryStage)
            stage.title = "Log"
            stage.scene = Scene(root, stageSize.x, stageSize.y)

            stage.maxHeight = stageSize.y
            stage.minHeight = stageSize.y
            stage.minWidth = stageSize.x

            stage.show()

//            stage.contentCenter = Main.primaryStage.contentCenter
        }
    }
}