@file:Suppress("unused")

package org.frice

import org.frice.event.OnClickEvent
import org.frice.event.OnMouseEvent
import org.frice.obj.*
import org.frice.obj.button.FButton
import org.frice.obj.button.SimpleButton
import org.frice.obj.effects.LineEffect
import org.frice.obj.sub.*
import org.frice.platform.*
import org.frice.platform.adapter.JvmDrawer
import org.frice.platform.adapter.JvmImage
import org.frice.resource.graphics.ColorResource
import org.frice.resource.image.ImageResource
import org.frice.utils.graphics.shape.FOval
import org.frice.utils.graphics.shape.FRectangle
import org.frice.utils.message.FDialog
import org.frice.utils.message.FLog
import org.frice.utils.misc.unless
import org.frice.utils.time.*
import java.awt.BorderLayout
import java.awt.Point
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.imageio.ImageIO.read
import javax.swing.JFrame

/**
 * First game class(not for you)
 *
 * Standard library, mainly for GUI.
 * some other library is in @see
 * The base game class.
 * this class do rendering, and something which are invisible to
 * game developer.
 *
 * DO NOT override the constructor.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
@Suppress("LeakingThis")
open class Game
@JvmOverloads
constructor(layerCount: Int = 1) : JFrame(), FriceGame {

	override val timeListeners = LinkedList<FTimeListener>()
	override val timeListenerDeleteBuffer = ArrayList<FTimeListener>()
	override val timeListenerAddBuffer = ArrayList<FTimeListener>()
	override val layers = Array(layerCount) { Layer() }

	var paused = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override var stopped = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override var debug = true

	override val random = Random()

	override var autoGC = true

	/**
	 * if true, there will be a fps calculating on the bottom-left side of window.
	 */
	override var showFPS = true

	override var loseFocus = false
		set(value) = Unit

	override var loseFocusChangeColor = true

	internal val refresh = FTimer(4)
	override var millisToRefresh: Int
		get () = refresh.time
		set (value) {
			refresh.time = value
		}

	internal val panel: GamePanel = GamePanel(this)

	override val drawer: JvmDrawer

	override var fpsCounter = 0
	override var fpsDisplay = 0
	override var fpsTimer = FTimer(1000)

	/**
	 * represent the mouse as an object
	 */
	@JvmField
	val mouse = object : AbstractObject {
		override var x: Double
			get() = panel.mousePosition?.getX() ?: -1.0
			set(value) {
				panel.mousePosition?.setLocation(value, y)
			}

		override var y: Double
			get() = panel.mousePosition?.getY() ?: -1.0
			set(value) {
				panel.mousePosition?.setLocation(x, value)
			}

		override var rotate = 0.0
		override fun toString() = "($x, $y)"
	}

	init {
		isResizable = false
		layout = BorderLayout()
		// set icon
		iconImage = read(javaClass.getResourceAsStream("/icon.png"))

		/// to prevent this engine from the call#cking NPE!!
		this.add(panel, BorderLayout.CENTER)
		bounds = Initializer.BIG_SQUARE
		onInit()
		drawer = JvmDrawer(this)
		drawer.init()
		isVisible = true
		FLog.i("If the window doesn't appear, please call `launch(YourGameClass.class)` instead of the constructor.")
	}

	fun mouse(e: OnMouseEvent) = layers.forEach {
		it.texts.forEach { b -> if (b is org.frice.obj.button.FButton && b.containsPoint(e.event.x, e.event.y)) b onMouse e }
	}

	fun click(e: OnClickEvent) = layers.forEach {
		it.texts.forEach { b -> if (b is org.frice.obj.button.FButton && b.containsPoint(e.event.x, e.event.y)) b onClick e }
	}

	override fun onInit() = Unit
	override fun onLastInit() = Unit
	override fun onRefresh() = Unit
	open fun onClick(e: OnClickEvent) = Unit
	open fun onMouse(e: OnMouseEvent) = Unit
	override fun onExit() {
		if (FDialog(this).confirm("Are you sure to exit?", "Ensuring", FDialog.YES_NO_OPTION) ==
				FDialog.YES_OPTION) {
			dispose()
			System.exit(0)
		} else return
	}

	override fun onLoseFocus() {
		paused = true
	}

	override fun onFocus() {
		paused = false
	}

	override fun customDraw(g: FriceDrawer) = Unit

	/**
	 * for kotlin only
	 * add keyboard listeners with lambda
	 */
	fun addKeyListener(
			typed: ((KeyEvent) -> Unit)? = null,
			pressed: ((KeyEvent) -> Unit)? = null,
			released: ((KeyEvent) -> Unit)? = null) {
		addKeyListener(object : KeyListener {
			override fun keyPressed(e: KeyEvent) {
				if (null != pressed) pressed(e)
			}

			override fun keyReleased(e: KeyEvent) {
				if (null != released) released(e)
			}

			override fun keyTyped(e: KeyEvent) {
				if (null != typed) typed(e)
			}
		})
	}

	inline fun addKeyTypedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(typed = { e -> if (e.keyCode == keyCode) key(e) })

	inline fun addKeyPressedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(pressed = { e -> if (e.keyCode == keyCode) key(e) })

	inline fun addKeyReleasedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(released = { e -> if (e.keyCode == keyCode) key(e) })

	infix fun setCursor(o: ImageResource) = setCursor(ImageObject(o))
	infix fun setCursor(o: ImageObject) {
		cursor = toolkit.createCustomCursor((o.image as JvmImage).image, Point(0, 0), "cursor")
	}

	override fun drawEverything(bgg: JvmDrawer) {
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
				if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
				else bgg.rotate(o.rotate, o.x, o.y)
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
				if (b.getColor() == ColorResource.COLORLESS) return@loop
				bgg.run {
					restore()
					init()
					rotate(b.rotate)
					setStringSize(b.textSize)
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
							bgg.color = b.getColor()
							bgg.drawRoundRect(b.x, b.y,
									b.width, b.height,
									Math.min(b.width * 0.5, 10.0),
									Math.min(b.height * 0.5, 10.0))
							bgg.color = ColorResource.DARK_GRAY
							bgg.drawString(b.text, b.x + 10, b.y + b.height / 2)
						}
					}
				} else {
					bgg.color = b.getColor()
					bgg.drawString(b.text, b.x, b.y)
				}
			}
		}
		customDraw(bgg)
	}

	override fun clearScreen() {
		drawer.color = ColorResource.WHITE
		drawer.drawRect(0.0, 0.0, width.toDouble(), height.toDouble())
		drawer.restore()
	}

}
