package org.frice

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.frice.event.*
import org.frice.obj.FObject
import org.frice.obj.PhysicalObject
import org.frice.obj.button.FButton
import org.frice.obj.button.SimpleButton
import org.frice.obj.effects.LineEffect
import org.frice.obj.sub.ShapeObject
import org.frice.platform.*
import org.frice.platform.adapter.JfxDrawer
import org.frice.resource.graphics.ColorResource
import org.frice.utils.misc.loop
import org.frice.utils.misc.unless
import org.frice.utils.shape.FOval
import org.frice.utils.shape.FRectangle
import org.frice.utils.time.FClock
import org.frice.utils.time.FTimer
import java.util.*
import kotlin.concurrent.thread

/**
 * @author ice1000
 * @since v1.5.0
 */
open class GameFX
@JvmOverloads
constructor(
	private val width: Int = 500,
	private val height: Int = 500,
	layerCount: Int = 1) : Application(), FriceGame {
	override fun getWidth() = width
	override fun getHeight() = height

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

	override val layers = Layers(layerCount) { Layer() }
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

	override fun onInit() = Unit
	override fun onLastInit() = Unit
	override fun onRefresh() = Unit
	override fun onMouse(e: OnMouseEvent) = Unit
	override fun customDraw(g: FriceDrawer) = Unit

	override fun getTitle(): String = stage.title
	override fun setTitle(title: String) {
		stage.title = title
	}

	private val canvas = Canvas(width.toDouble(), height.toDouble())
	private val root = StackPane(canvas)
	override val drawer = JfxDrawer(canvas.graphicsContext2D)

	override fun onExit() {
		TODO("not implemented")
	}


	fun drawEverything(bgg: JfxDrawer) {
		processBuffer()
		layers.forEach {
			it.objects.forEach { o ->
				if (o is org.frice.obj.FObject) {
					o.runAnims()
					o.checkCollision()
				}
			}

			it.objects.forEach loop@ { o ->
				if (o is ShapeObject && ColorResource.COLORLESS == o.getResource()) return@loop
				if (o is LineEffect && ColorResource.COLORLESS == o.color) return@loop
				bgg.run {
					restore()
					init()
				}
				// if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
				// else bgg.rotate(o.rotate, o.x, o.y)
				bgg.rotate(o.rotate)
				when (o) {
					is FObject.ImageOwner -> {
						unless(o.x + o.image.width <= 0 ||
							o.x >= width ||
							o.y + o.image.height <= 0 ||
							o.y >= height) {
							bgg.drawImage(o.image, o.x, o.y)
						}
					}
					is ShapeObject -> {
						unless((o.x + o.width) <= 0 ||
							o.x >= width ||
							(o.y + o.height) <= 0 ||
							o.y >= height) {
							bgg.color = o.getResource()
							when (o.collideBox) {
								is FRectangle -> bgg.drawRect(o.x, o.y, o.width, o.height)
								is FOval -> bgg.drawOval(o.x, o.y, o.width, o.height)
							}
						}
					}
					is LineEffect -> {
						bgg.color = o.color
						bgg.drawLine(o.x, o.y, o.x2, o.y2)
					}
				}
				if (autoGC && (o.x < -width || o.x > width + width || o.y < -height || o.y > height + height)) {
					if (o is PhysicalObject) o.died = true
					it.removeObject(o)
					//FLog.i("o.x = ${o.x}, width = $width," +
					//		" o.y = ${o.y}, height = $height")
				}
			}

			it.texts.forEach loop@ { b ->
				if (b.color == ColorResource.COLORLESS) return@loop
				bgg.run {
					restore()
					init()
					rotate(b.rotate)
					stringSize(b.textSize)
				}
				if (b is FButton) {
					when (b) {
						is FObject.ImageOwner -> {
							unless(b.x + b.image.width < 0 ||
								b.x > width ||
								b.y + b.image.height < 0 ||
								b.y > height) {
								bgg.drawImage(b.image, b.x, b.y)
							}
						}
						is SimpleButton -> {
							bgg.color = b.color
							bgg.drawRoundRect(b.x, b.y,
								b.width, b.height,
								Math.min(b.width * 0.5, 10.0),
								Math.min(b.height * 0.5, 10.0))
							bgg.color = ColorResource.DARK_GRAY
							bgg.drawString(b.text, b.x + 10, b.y + b.height / 2)
						}
					}
				} else {
					bgg.color = b.color
					bgg.drawString(b.text, b.x, b.y)
				}
			}
		}
		customDraw(bgg)
	}

	override fun start(stage: Stage) {
		this.stage = stage
		scene = Scene(root, width.toDouble(), height.toDouble())
		canvas.setOnMouseClicked { onMouse(fxMouse(it, MOUSE_CLICKED)) }
		canvas.setOnMouseEntered { onMouse(fxMouse(it, MOUSE_ENTERED)) }
		canvas.setOnMouseExited { onMouse(fxMouse(it, MOUSE_EXITED)) }
		canvas.setOnMousePressed { onMouse(fxMouse(it, MOUSE_PRESSED)) }
		canvas.setOnMouseReleased { onMouse(fxMouse(it, MOUSE_RELEASED)) }
		stage.scene = scene
		onInit()
		stage.show()
		thread {
			onLastInit()
			loop {
				try {
					onRefresh()
					if (!paused && !stopped && refresh.ended()) {
						// TODO("repaint")
						customDraw(drawer)
					}
				} catch (ignored: ConcurrentModificationException) {
				}
			}
		}
	}
}
