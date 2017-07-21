package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnKeyEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.obj.FObject
import org.frice.game.obj.PhysicalObject
import org.frice.game.obj.button.FButton
import org.frice.game.obj.button.FText
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.effects.LineEffect
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.platform.FriceGame
import org.frice.game.platform.adapter.JvmDrawer
import org.frice.game.platform.adapter.JvmImage
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.misc.forceRun
import org.frice.game.utils.misc.loop
import org.frice.game.utils.misc.unless
import org.frice.game.utils.time.Clock
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import java.awt.BorderLayout
import java.awt.Graphics
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.*
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.WindowConstants
import kotlin.concurrent.thread

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
open class Game : JFrame(), FriceGame {

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
	}

	override val objects = LinkedList<AbstractObject>()
	override val objectDeleteBuffer = ArrayList<AbstractObject>()
	override val objectAddBuffer = ArrayList<AbstractObject>()

	override val timeListeners = LinkedList<FTimeListener>()
	override val timeListenerDeleteBuffer = ArrayList<FTimeListener>()
	override val timeListenerAddBuffer = ArrayList<FTimeListener>()

	override val texts = LinkedList<FText>()
	override val textDeleteBuffer = ArrayList<FText>()
	override val textAddBuffer = ArrayList<FText>()

	/**
	 * if paused, main window will not call `onRefresh()`.
	 */
	var paused = false
		set(value) {
			if (value) Clock.pause()
			else Clock.resume()
			field = value
		}

	/**
	 * not implemented yet.
	 * currently it's same as paused.
	 */
	var stopped = false
		set(value) {
			if (value) Clock.pause()
			else Clock.resume()
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
		protected set

	var loseFocusChangeColor = true

	private val refresh = FTimer(4)

	private val panel: GamePanel

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
			get() = mousePosition.getX()
			set(value) = Unit

		override var y: Double
			get() = mousePosition.getY()
			set(value) = Unit

		override var rotate = 0.0
	}

	init {
		panel = GamePanel()
		isResizable = false
		defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
		layout = BorderLayout()
		// set icon
		// iconImage = javax.imageio.ImageIO.read(javaClass.getResourceAsStream("/icon.png"))

		/// to prevent this engine from the call#cking NPE!!
		this.add(panel, BorderLayout.CENTER)
		bounds = BIG_SQUARE
		onInit()
		drawer = JvmDrawer(this)
		drawer.init()
		isVisible = true

		thread {
			FLog.v("Engine start!")
			onLastInit()
			loop {
				forceRun {
					onRefresh()
					timeListeners.forEach { it.check() }
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
	}

	protected fun mouse(e: OnMouseEvent) =
			texts.forEach { b ->
				if (b is FButton && b.containsPoint(e.event.x, e.event.y)) b onMouse e
			}

	protected fun click(e: OnClickEvent) =
			texts.forEach { b ->
				if (b is FButton && b.containsPoint(e.event.x, e.event.y)) b onClick e
			}

	override fun onInit() = Unit
	override fun onLastInit() = Unit
	override fun onRefresh() = Unit
	open fun onClick(e: OnClickEvent) = Unit
	open fun onMouse(e: OnMouseEvent) = Unit
	override fun onExit() {
		if (FDialog(this).confirm("Are you sure to exit?",
				"Ensuring", JOptionPane.YES_NO_OPTION) ==
				JOptionPane.YES_OPTION)
			System.exit(0)
		else return
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
	@JvmOverloads
	fun addKeyListener(
			typed: (KeyEvent) -> Unit = { },
			pressed: (KeyEvent) -> Unit = { },
			released: (KeyEvent) -> Unit = { }) {
		addKeyListener(object : KeyListener {
			override fun keyPressed(e: KeyEvent?) = pressed(e!!)
			override fun keyReleased(e: KeyEvent?) = released(e!!)
			override fun keyTyped(e: KeyEvent?) = typed(e!!)
		})
	}

	fun listenKeyPressed(key: OnKeyEvent) = listenKeyPressed(key::execute)
	inline infix fun listenKeyPressed(crossinline key: (KeyEvent) -> Unit) =
			addKeyListener({ key.invoke(it) }, { key.invoke(it) }, { key.invoke(it) })

	fun addKeyTypedEvent(keyCode: Int, key: OnKeyEvent) = addKeyTypedEvent(keyCode, key::execute)
	inline fun addKeyTypedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) = addKeyListener(typed = { e ->
		if (e.keyCode == keyCode) key.invoke(e)
	})

	fun addKeyPressedEvent(keyCode: Int, key: OnKeyEvent) =
			addKeyPressedEvent(keyCode, key::execute)

	inline fun addKeyPressedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(pressed = { e ->
				if (e.keyCode == keyCode) key.invoke(e)
			})

	fun addKeyReleasedEvent(keyCode: Int, key: OnKeyEvent) =
			addKeyReleasedEvent(keyCode, key::execute)

	inline fun addKeyReleasedEvent(keyCode: Int, crossinline key: (KeyEvent) -> Unit) =
			addKeyListener(released = { e ->
				if (e.keyCode == keyCode) key.invoke(e)
			})

	infix fun setCursor(o: ImageResource) = setCursor(ImageObject(o))
	infix fun setCursor(o: ImageObject) {
		cursor = toolkit.createCustomCursor((o.image as JvmImage).image, Point(0, 0), "cursor")
	}

	override fun addObject(vararg objs: AbstractObject) = objs.forEach(this::addObject)

	override infix fun addObject(obj: AbstractObject) {
		if (obj is FText) textAddBuffer.add(obj)
		else objectAddBuffer.add(obj)
	}

	override fun clearObjects() {
		objectDeleteBuffer.addAll(objects)
		textDeleteBuffer.addAll(texts)
	}

	override infix fun removeObject(obj: AbstractObject) {
		if (obj is FText) textDeleteBuffer.add(obj)
		else objectDeleteBuffer.add(obj)
	}

	override fun removeObject(vararg objs: AbstractObject) =
			objs.forEach(this::removeObject)

	override fun addTimeListener(vararg listeners: FTimeListener) =
			listeners.forEach { addTimeListener(it) }

	override infix fun addTimeListener(listener: FTimeListener) =
			timeListenerAddBuffer.add(listener)

	override fun clearTimeListeners() =
			timeListenerDeleteBuffer.addAll(timeListeners)

	override fun removeTimeListener(vararg listeners: FTimeListener) =
			listeners.forEach { removeTimeListener(it) }

	override infix fun removeTimeListener(listener: FTimeListener) =
			timeListenerDeleteBuffer.add(listener)

	override fun drawEverything(bgg: JvmDrawer) {
		processBuffer()

		objects.forEach { o ->
			if (o is FObject) {
				o.runAnims()
				o.checkCollision()
			}
		}

		objects.forEach { o ->
			bgg.restore()
			bgg.init()
			if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
			else bgg.rotate(o.rotate, o.x, o.y)
			when (o) {
				is FObject.ImageOwner ->
					unless(o.x + o.image.width < 0 || o.x > width || o.y + o.image.height < 0 || o.y > height) {
						bgg.drawImage(o.image, o.x, o.y)
					}
				is ShapeObject ->
					unless((o.x + o.width) < 0 || o.x > width || (o.y + o.height) < 0 || o.y > height) {
						bgg.color = o.getResource()
						when (o.collideBox) {
							is FRectangle -> bgg.drawRect(o.x, o.y, o.width, o.height)
							is FOval -> bgg.drawOval(o.x, o.y, o.width, o.height)
						}
					}
				is LineEffect -> bgg.drawLine(o.x, o.y, o.x2, o.y2)
			}
			if (autoGC && (o.x < -width || o.x > width + width || o.y < -height || o.y > height + height)) {
				if (o is PhysicalObject) o.died = true
				removeObject(o)
				//FLog.i("o.x = ${o.x}, width = $width," +
				//		" o.y = ${o.y}, height = $height")
			}
		}

		texts.forEach {
			b ->
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
		customDraw(bgg)
	}

	/**
	 * get a screenShot.
	 *
	 * @return screen cut as an image
	 */
	override fun getScreenCut() = ImageResource(drawer.friceImage)

	/**
	 * this method escaped the error
	 *
	 * @return exact position of the mouse
	 */
	override fun getMousePosition() = panel.mousePosition!!

	override fun clearScreen() {
		drawer.color = ColorResource.WHITE
		drawer.drawRect(0.0, 0.0, width.toDouble(), height.toDouble())
		drawer.restore()
	}

	/**
	 * Demo24 game view.
	 * all rendering work and game object calculating are here.
	 *
	 * Created by ice1000 on 2016/8/13.
	 * @author ice1000
	 * @since v0.1
	 */
	inner class GamePanel : JPanel() {
		init {
			addMouseListener(object : MouseListener {
				override fun mouseClicked(e: MouseEvent) {
					click(OnMouseEvent.create(e, OnMouseEvent.MOUSE_CLICK))
					onClick(OnClickEvent.create(e))
				}

				override fun mouseEntered(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_ENTERED))
				override fun mouseExited(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_EXITED))
				override fun mouseReleased(e: MouseEvent) {
					mouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
					onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
				}

				override fun mousePressed(e: MouseEvent) {
					mouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
					onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
				}
			})

			addWindowListener(object : WindowListener {
				override fun windowDeiconified(e: WindowEvent) = Unit
				override fun windowActivated(e: WindowEvent) {
					loseFocus = false
					Clock.resume()
					onFocus()
				}

				override fun windowDeactivated(e: WindowEvent) {
					loseFocus = true
					Clock.pause()
					onLoseFocus()
				}

				override fun windowIconified(e: WindowEvent) = Unit
				override fun windowClosing(e: WindowEvent) = onExit()
				override fun windowClosed(e: WindowEvent) = Unit
				override fun windowOpened(e: WindowEvent) = Unit
			})
		}

		override fun update(g: Graphics?) = paint(g)
		override fun paintComponent(g: Graphics) {
			clearScreen()
			drawEverything(drawer)

			if (loseFocus && loseFocusChangeColor) {
				loop(drawer.friceImage.width) { x ->
					loop(drawer.friceImage.height) { y ->
						drawer.friceImage.set(x, y, ColorResource(drawer
								.friceImage[x, y]
								.color
								.darker()))
					}
				}
			}

			if (showFPS) drawer.drawString("fps: $fpsDisplay", 30.0, height - 30.0)

			/*
			 * 厚颜无耻
			 * drawer.drawString("Powered by FriceEngine. ice1000", 5, 20)
			 */
			g.drawImage(drawer.friceImage.image, 0, 0, this)
		}
	}

}