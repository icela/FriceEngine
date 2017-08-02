@file:Suppress("unused")

package org.frice.game

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel
import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.obj.FObject
import org.frice.game.obj.PhysicalObject
import org.frice.game.obj.button.FButton
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.effects.LineEffect
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.platform.FriceGame
import org.frice.game.platform.Layer
import org.frice.game.platform.adapter.JvmDrawer
import org.frice.game.platform.adapter.JvmImage
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.misc.async
import org.frice.game.utils.misc.loop
import org.frice.game.utils.misc.unless
import org.frice.game.utils.time.Clock
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import java.awt.BorderLayout
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.imageio.ImageIO.read
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.WindowConstants

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

	companion object {
		@JvmField
		val TO_X = 100
		@JvmField
		val TO_Y = 100

		@JvmField
		val SMALL_PHONE = Rectangle(TO_X, TO_Y, 480, 800)
		@JvmField
		val BIG_PHONE = Rectangle(TO_X, TO_Y, 720, 1200)
		@JvmField
		val HUGE_PHONE = Rectangle(TO_X, TO_Y, 1080, 1920)

		@JvmField
		val SMALL_SQUARE = Rectangle(TO_X, TO_Y, 400, 400)
		@JvmField
		val BIG_SQUARE = Rectangle(TO_X, TO_Y, 800, 800)

		@JvmStatic
		fun Rectangle.rotate() {
			width -= -height
			height -= width
			width += height
		}

		@JvmStatic
		fun launch(c: Class<out Game>) {
			val game = c.newInstance()
			game.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
			FLog.v("Engine start!")
			if ("Windows" in System.getProperty("os.name")) UIManager.setLookAndFeel(WindowsLookAndFeel())
			else UIManager.setLookAndFeel(GTKLookAndFeel())
			game.run {
				async {
					onLastInit()
					loop {
						onRefresh()
						timeListeners.forEach(FTimeListener::check)
						// only update per "refreshTime"
						if (!paused && !stopped && refresh.ended()) {
							panel.repaint()
							++fpsCounter
							if (fpsTimer.ended()) {
								fpsDisplay = fpsCounter
								fpsCounter = 0
							}
						}
					}
				}
				FLog.v("Engine thread exited.")
			}
			FLog.v("Engine thread successfully created.")
		}
	}

	override val timeListeners = LinkedList<FTimeListener>()
	override val timeListenerDeleteBuffer = ArrayList<FTimeListener>()
	override val timeListenerAddBuffer = ArrayList<FTimeListener>()
	override val layers = Array(layerCount) { Layer() }

	/**
	 * if paused, main window will not call `onRefresh()`.
	 */
	var paused = false
		set(value) {
			if (value) Clock.pause() else Clock.resume()
			field = value
		}

	/**
	 * not implemented yet.
	 * currently it's same as paused.
	 */
	var stopped = false
		set(value) {
			if (value) Clock.pause() else Clock.resume()
			field = value
		}

	var debug = true

	/**
	 * a general purpose instance for generating random numbers
	 */
	val random = Random()

	/**
	 * if true, the engine will collect all objects which are invisible from game window.
	 */
	var autoGC = true

	/**
	 * if true, there will be a fps calculating on the bottom-left side of window.
	 */
	var showFPS = true

	var loseFocus = false
		internal set

	var loseFocusChangeColor = true

	private val refresh = FTimer(4)
	var millisToRefresh: Int
		get () = refresh.time
		set (value) {
			refresh.time = value
		}

	private val panel: GamePanel = GamePanel(this)

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
			get() = mousePosition?.getX() ?: -1.0
			set(value) {
				mousePosition?.setLocation(value, y)
			}

		override var y: Double
			get() = mousePosition?.getY() ?: -1.0
			set(value) {
				mousePosition?.setLocation(x, value)
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
		bounds = BIG_SQUARE
		onInit()
		drawer = JvmDrawer(this)
		drawer.init()
		isVisible = true
		FLog.i("If the window doesn't appear, please call `launch(YourGameClass.class)` instead of the constructor.")
	}

	fun mouse(e: OnMouseEvent) = layers.forEach {
		it.texts.forEach { b -> if (b is FButton && b.containsPoint(e.event.x, e.event.y)) b onMouse e }
	}

	fun click(e: OnClickEvent) = layers.forEach {
		it.texts.forEach { b -> if (b is FButton && b.containsPoint(e.event.x, e.event.y)) b onClick e }
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

	override fun customDraw(g: JvmDrawer) = Unit

	/**
	 * for kotlin only
	 * add keyboard listeners with lambda
	 */
	fun addKeyListener(
			typed: ((KeyEvent) -> Unit)? = null,
			pressed: ((KeyEvent) -> Unit)? = null,
			released: ((KeyEvent) -> Unit)? = null) {
		addKeyListener(object : KeyListener {
			override fun keyPressed(e: KeyEvent?) {
				if (null != pressed && null != e) pressed(e)
			}

			override fun keyReleased(e: KeyEvent?) {
				if (null != released && null != e) released(e)
			}

			override fun keyTyped(e: KeyEvent?) {
				if (null != typed && null != e) typed(e)
			}
		})
	}

	inline fun addKeyTypedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(typed = { e ->
				if (e.keyCode == keyCode) key(e)
			})

	inline fun addKeyPressedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(pressed = { e ->
				if (e.keyCode == keyCode) key(e)
			})

	inline fun addKeyReleasedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(released = { e ->
				if (e.keyCode == keyCode) key(e)
			})

	infix fun setCursor(o: ImageResource) = setCursor(ImageObject(o))
	infix fun setCursor(o: ImageObject) {
		cursor = toolkit.createCustomCursor((o.image as JvmImage).image, Point(0, 0), "cursor")
	}

	override fun drawEverything(bgg: JvmDrawer) {
		processBuffer()
		layers.forEach {
			it.objects.forEach { o ->
				if (o is FObject) {
					o.runAnims()
					o.checkCollision()
				}
			}

			it.objects.forEach loop@ { o ->
				if (o is ShapeObject && ColorResource.COLORLESS == o.getResource()) return@loop
				if (o is LineEffect && ColorResource.COLORLESS == o.color) return@loop
				bgg.restore()
				bgg.init()
				if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
				else bgg.rotate(o.rotate, o.x, o.y)
				when (o) {
					is FObject.ImageOwner ->
						unless(o.x + o.image.width < 0 || o.x > width || o.y + o.image.height < 0 || o.y > height) {
							bgg.drawImage(o.image, o.x, o.y)
						}
					is ShapeObject -> {
						unless((o.x + o.width) < 0 || o.x > width || (o.y + o.height) < 0 || o.y > height) {
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

	/**
	 * this method escaped the error
	 *
	 * @return exact position of the mouse
	 */
	override fun getMousePosition(): Point? = panel.mousePosition

	override fun clearScreen() {
		drawer.color = ColorResource.WHITE
		drawer.drawRect(0.0, 0.0, width.toDouble(), height.toDouble())
		drawer.restore()
	}

}
