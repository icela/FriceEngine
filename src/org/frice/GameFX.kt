package org.frice

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.frice.event.OnClickEvent
import org.frice.event.OnMouseEvent
import org.frice.platform.*
import org.frice.utils.time.*
import java.util.*

open class GameFX
@JvmOverloads
constructor(layerCount: Int = 1) : Application(), FriceGame {
	override var paused = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override var stopped = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override val timeListeners = LinkedList<FTimeListener>()
	override val timeListenerDeleteBuffer = ArrayList<FTimeListener>()
	override val timeListenerAddBuffer = ArrayList<FTimeListener>()
	override val layers = Array(layerCount) { Layer() }
	override var debug = true
	override val random = Random()
	override var autoGC = true
	override var showFPS = true
	override var loseFocus = false
		set(value) = Unit

	var fpsCounter = 0
	var fpsDisplay = 0
	var fpsTimer = FTimer(1000)

	override var loseFocusChangeColor = true

	internal val refresh = FTimer(4)
	override var millisToRefresh: Int
		get () = refresh.time
		set (value) {
			refresh.time = value
		}

	override fun onInit() = Unit
	override fun onLastInit() = Unit
	override fun onRefresh() = Unit
	open fun onClick(e: OnClickEvent) = Unit
	open fun onMouse(e: OnMouseEvent) = Unit

	override fun setTitle(title: String) {
		TODO("not implemented")
	}

	override fun getTitle(): String {
		TODO("not implemented")
	}

	override val drawer: FriceDrawer
		get() = TODO("not implemented")

	override fun onExit() {
		TODO("not implemented")
	}

	override fun customDraw(g: FriceDrawer) {
		TODO("not implemented")
	}

	override fun clearScreen() {
		TODO("not implemented")
	}

	override fun start(stage: Stage) {
		val btn = Button()
		btn.text = "Say 'Hello World'"
		btn.onAction = EventHandler<ActionEvent> { println("Hello World!") }

		val root = StackPane()
		root.children.add(btn)

		val scene = Scene(root, 300.0, 250.0)

		stage.title = "Hello World!"
		stage.scene = scene
		stage.show()
	}
}
