package org.frice

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.frice.event.*
import org.frice.platform.FriceGame
import org.frice.platform.Layer
import org.frice.platform.adapter.JfxDrawer
import org.frice.resource.graphics.ColorResource
import org.frice.utils.loop
import org.frice.utils.time.FClock
import org.frice.utils.time.FTimer
import java.util.*
import kotlin.concurrent.thread

/**
 * @author ice1000
 * @since v1.5.0
 */
open class GameFX @JvmOverloads constructor(
	private val width: Int = 1000,
	private val height: Int = 1000,
	layerCount: Int = 1) : Application(), FriceGame {

	override fun isResizable() = stage.isResizable
	override fun setResizable(resizable: Boolean) {
		stage.isResizable = resizable
	}

	override fun getWidth() = width
	override fun getHeight() = height

	override var isFullScreen: Boolean
		get() = stage.isFullScreen
		set(value) {
			stage.isFullScreen = value
		}

	var isAlwaysOnTop: Boolean
		get() = stage.isAlwaysOnTop
		set(value) {
			stage.isAlwaysOnTop = value
		}

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

	override val layers = Array(layerCount) { Layer() }
	override var debug = true
	override var autoGC = true
	override var showFPS = true
	override var loseFocus = false
		set(value) = Unit

	private lateinit var stage: Stage
	private lateinit var scene: Scene

	var fpsCounter = 0
	var fpsDisplay = 0
	var fpsTimer = FTimer(1000)

	override var loseFocusChangeColor = true

	private val refresh = FTimer(4)
	override var millisToRefresh: Int
		get () = refresh.time
		set (value) {
			refresh.time = value
		}

	override fun getTitle(): String = stage.title
	override fun setTitle(title: String) {
		stage.title = title
	}

	private val canvas = Canvas(width.toDouble(), height.toDouble())
	private val root = StackPane(canvas)
	override val drawer = JfxDrawer(canvas.graphicsContext2D)

	override fun onExit() {
		Platform.exit()
		System.exit(0)
	}

	override fun start(stage: Stage) {
		this.stage = stage
		scene = Scene(root, width.toDouble(), height.toDouble())
		isResizable = false
		canvas.setOnMouseClicked {
			mouse(fxMouse(it, MOUSE_CLICKED))
			onMouse(fxMouse(it, MOUSE_CLICKED))
		}
		canvas.setOnMouseEntered { onMouse(fxMouse(it, MOUSE_ENTERED)) }
		canvas.setOnMouseExited { onMouse(fxMouse(it, MOUSE_EXITED)) }
		canvas.setOnMousePressed {
			mouse(fxMouse(it, MOUSE_PRESSED))
			onMouse(fxMouse(it, MOUSE_PRESSED))
		}
		canvas.setOnMouseReleased {
			mouse(fxMouse(it, MOUSE_RELEASED))
			onMouse(fxMouse(it, MOUSE_RELEASED))
		}
		stage.setOnCloseRequest {
			onExit()
			it.consume()
		}
		stage.scene = scene
		onInit()
		stage.icons += Image(javaClass.getResourceAsStream("/icon.png"))
		stage.show()
		thread {
			onLastInit()
			loop {
				try {
					onRefresh()
					if (!paused && !stopped && refresh.ended()) {
						clearScreen()
						drawEverything(drawer)
						drawer.init()
						drawer.color = ColorResource.DARK_GRAY
						++fpsCounter
						if (fpsTimer.ended()) {
							fpsDisplay = fpsCounter
							fpsCounter = 0
						}
						if (showFPS) drawer.drawString("fps: $fpsDisplay", 30.0, height - 30.0)
					}
				} catch (ignored: ConcurrentModificationException) {
				}
			}
		}
	}
}
