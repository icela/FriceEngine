package org.frice

import javafx.application.Application
import javafx.application.Platform
import javafx.embed.swing.SwingFXUtils
import javafx.event.EventHandler
import javafx.scene.*
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.input.KeyEvent
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.frice.event.*
import org.frice.platform.*
import org.frice.platform.adapter.*
import org.frice.resource.graphics.ColorResource
import org.frice.utils.loop
import org.frice.utils.time.*
import org.frice.utils.unless
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

	override var isAlwaysTop: Boolean
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

	private val fpsCounter = FpsCounter()

	override var loseFocusChangeColor = true

	private val refresh = FTimer(4)
	override var millisToRefresh: Int
		get () = refresh.time
		set (value) {
			refresh.time = value
		}

	private fun <T> initAlert(it: Dialog<T>, title: String) {
		it.isResizable = false
		it.title = title
		it.showAndWait()
	}

	override fun dialogConfirmYesNo(msg: String, title: String): Boolean =
		Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO).let {
			initAlert(it, title)
			return@let it.result == ButtonType.YES
		}

	override fun dialogShow(msg: String, title: String) {
		Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).let { initAlert(it, title) }
	}

	override fun dialogInput(msg: String, title: String): String = TextInputDialog().let {
		initAlert(it, title)
		return@let it.result
	}

	override val screenCut
		get() = JvmImage(SwingFXUtils.fromFXImage(
			canvas.snapshot(SnapshotParameters(), null), null))

	override fun getTitle(): String = stage.title
	override fun setTitle(title: String) {
		stage.title = title
	}

	var onKeyTyepd: EventHandler<in KeyEvent>
		get() = scene.onKeyTyped
		set(value) {
			scene.onKeyTyped = value
		}

	var onKeyPressed: EventHandler<in KeyEvent>
		get() = scene.onKeyPressed
		set(value) {
			scene.onKeyPressed = value
		}

	var onKeyReleased: EventHandler<in KeyEvent>
		get() = scene.onKeyReleased
		set(value) {
			scene.onKeyReleased = value
		}

	private val canvas = Canvas(width.toDouble(), height.toDouble())
	private val root = StackPane(canvas)
	override val drawer = JfxDrawer(canvas.graphicsContext2D)

	override fun setCursor(o: FriceImage) {
		scene.cursor = ImageCursor((o as JfxImage).jfxImage)
	}

	fun onExit(): Boolean {
		return false
	}

	override final fun start(stage: Stage) {
		this.stage = stage
		scene = Scene(root, width.toDouble(), height.toDouble())
		isResizable = false
		scene.setOnMouseClicked {
			mouse(fxMouse(it, MOUSE_CLICKED))
			onMouse(fxMouse(it, MOUSE_CLICKED))
		}
		scene.setOnMouseEntered { onMouse(fxMouse(it, MOUSE_ENTERED)) }
		scene.setOnMouseExited { onMouse(fxMouse(it, MOUSE_EXITED)) }
		scene.setOnMousePressed {
			mouse(fxMouse(it, MOUSE_PRESSED))
			onMouse(fxMouse(it, MOUSE_PRESSED))
		}
		scene.setOnMouseReleased {
			mouse(fxMouse(it, MOUSE_RELEASED))
			onMouse(fxMouse(it, MOUSE_RELEASED))
		}
		stage.setOnCloseRequest { unless(onExit(), it::consume) }
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
						fpsCounter.refresh()
						if (showFPS) drawer.drawString("fps: ${fpsCounter.display}", 30.0, height - 30.0)
					}
				} catch (ignored: ConcurrentModificationException) {
				}
			}
		}
	}
}
