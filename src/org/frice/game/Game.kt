package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnKeyEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.obj.FObject
import org.frice.game.obj.PhysicalObject
import org.frice.game.obj.button.FButton
import org.frice.game.obj.button.FText
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.effects.LineEffect
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.FResource
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.graphics.utils.ColorUtils
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.message.error.FatalError
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.misc.forceRun
import org.frice.game.utils.misc.loop
import org.frice.game.utils.misc.unless
import org.frice.game.utils.time.Clock
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import java.awt.*
import java.awt.event.*
import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO
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
abstract class Game() : JFrame() {
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

	@JvmField
	protected val objects = LinkedList<AbstractObject>()
	@JvmField
	protected val objectDeleteBuffer = ArrayList<AbstractObject>()
	@JvmField
	protected val objectAddBuffer = ArrayList<AbstractObject>()

	@JvmField
	protected val timeListeners = LinkedList<FTimeListener>()
	@JvmField
	protected val timeListenerDeleteBuffer = ArrayList<FTimeListener>()
	@JvmField
	protected val timeListenerAddBuffer = ArrayList<FTimeListener>()

	@JvmField
	protected val texts = LinkedList<FText>()
	@JvmField
	protected val textDeleteBuffer = ArrayList<FText>()
	@JvmField
	protected val textAddBuffer = ArrayList<FText>()

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

	/**
	 * background resource (don't setBackground, please use `setBack()` instead.)
	 */
	var back: FResource = ColorResource.LIGHT_GRAY
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

	private val refresh = FTimer(10)

	private val buffer: BufferedImage

	private val panel: GamePanel
	private val stableBuffer: BufferedImage

	private val bg: Graphics2D
		get() = buffer.graphics as Graphics2D

	private var fpsCounter = 0
	private var fpsDisplay = 0
	private val fpsTimer: FTimer

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
		isResizable = false
		defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
		layout = BorderLayout()
		// set icon
		iconImage = ImageIO.read(javaClass.getResourceAsStream("/icon.png"))

		/// to prevent this engine from the call#cking NPE!!
		panel = GamePanel()
		this.add(panel, BorderLayout.CENTER)
		bounds = BIG_SQUARE
		fpsTimer = FTimer(1000)
		onInit()
		buffer = BufferedImage(panel.width, panel.height, BufferedImage.TYPE_INT_ARGB)
		stableBuffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
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

	protected open fun onInit() = Unit
	protected open fun onLastInit() = Unit
	protected open fun onRefresh() = Unit
	protected open fun onClick(e: OnClickEvent) = Unit
	protected open fun onMouse(e: OnMouseEvent) = Unit
	protected open fun onExit() {
		if (FDialog(this).confirm("Are you sure to exit?",
				"Ensuring", JOptionPane.YES_NO_OPTION) ==
				JOptionPane.YES_OPTION)
			System.exit(0)
		else return
	}

	protected open fun onLoseFocus(e: OnWindowEvent?) {
		paused = true
	}

	protected open fun onFocus(e: OnWindowEvent?) {
		paused = false
	}

	protected open fun customDraw(g: Graphics2D) = Unit

	/**
	 * for kotlin only
	 * add keyboard listeners with lambda
	 */
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

	fun listenKeyPressed(key: OnKeyEvent) = listenKeyPressed({ e -> key.execute(e) })
	infix fun listenKeyPressed(key: (KeyEvent) -> Unit) =
			addKeyListener({ key.invoke(it) }, { key.invoke(it) }, { key.invoke(it) })

	fun addKeyTypedEvent(keyCode: Int, key: OnKeyEvent) = addKeyTypedEvent(keyCode, { e -> key.execute(e) })
	fun addKeyTypedEvent(keyCode: Int, key: (KeyEvent) -> Unit) = addKeyListener(typed = { e ->
		if (e.keyCode == keyCode) key.invoke(e)
	})

	fun addKeyPressedEvent(keyCode: Int, key: OnKeyEvent) =
			addKeyPressedEvent(keyCode, { e -> key.execute(e) })

	fun addKeyPressedEvent(keyCode: Int, key: (KeyEvent) -> Unit) =
			addKeyListener(pressed = { e ->
				if (e.keyCode == keyCode) key.invoke(e)
			})

	fun addKeyReleasedEvent(keyCode: Int, key: OnKeyEvent) =
			addKeyReleasedEvent(keyCode, { e -> key.execute(e) })

	fun addKeyReleasedEvent(keyCode: Int, key: (KeyEvent) -> Unit) =
			addKeyListener(released = { e ->
				if (e.keyCode == keyCode) key.invoke(e)
			})

	infix fun setCursor(o: ImageResource) = setCursor(ImageObject(o))
	infix fun setCursor(o: ImageObject) {
		cursor = toolkit.createCustomCursor(o.image, Point(0, 0), "cursor")
	}


	/**
	 * adds objects
	 *
	 * @param objs as a collection
	 */
	infix fun addObjects(objs: Collection<AbstractObject>) = addObjects(objs.toTypedArray())

	/**
	 * adds objects
	 *
	 * @param objs as an array
	 */
	infix fun addObjects(objs: Array<AbstractObject>) = objs.forEach { o -> addObject(o) }

	/**
	 * add Objects using vararg
	 */
	fun addObject(vararg objs: AbstractObject) = objs.forEach { o -> addObject(o) }

	/**
	 * adds an object to game, to be shown on game window.
	 */
	infix fun addObject(obj: AbstractObject) {
		if (obj is FText) textAddBuffer.add(obj)
		else objectAddBuffer.add(obj)
	}

	/**
	 * clears all objects.
	 * this method is safe.
	 */
	fun clearObjects() {
		objectDeleteBuffer.addAll(objects)
		textDeleteBuffer.addAll(texts)
	}

	/**
	 * removes objects.
	 * this method is safe.
	 *
	 * @param objs will remove objects which is equal to them, as an array.
	 */
	infix fun removeObjects(objs: Array<AbstractObject>) = objs.forEach { o -> objectDeleteBuffer.add(o) }

	/**
	 * removes objects.
	 * this method is safe.
	 *
	 * @param objs will remove objects which is equal to them, as a collection.
	 */
	infix fun removeObjects(objs: Collection<AbstractObject>) = removeObjects(objs.toTypedArray())

	/**
	 * removes single object.
	 * this method is safe.
	 *
	 * @param obj will remove objects which is equal to it.
	 */
	infix fun removeObject(obj: AbstractObject) {
		if (obj is FText) textDeleteBuffer.add(obj)
		else objectDeleteBuffer.add(obj)
	}

	/**
	 * remove Objects using vararg
	 */
	fun removeObject(vararg objs: AbstractObject) = objs.forEach { o -> removeObject(o) }

	/**
	 * adds a auto-executed time listener
	 * you must add or it won't work.
	 */
	infix fun addTimeListener(listener: FTimeListener) = timeListenerAddBuffer.add(listener)

	/**
	 * add TimeListeners using vararg
	 */
	fun addTimeListener(vararg listeners: FTimeListener) = listeners.forEach { l -> addTimeListener(l) }

	/**
	 * adds an array of auto-executed time listeners
	 */
	infix fun addTimeListeners(listeners: Array<FTimeListener>) = listeners.forEach { l -> addTimeListener(l) }

	/**
	 * adds a collection of auto-executed time listeners
	 */
	infix fun addTimeListeners(listeners: Collection<FTimeListener>) = addTimeListeners(listeners.toTypedArray())

	/**
	 * removes all auto-executed time listeners
	 */
	fun clearTimeListeners() = timeListenerDeleteBuffer.addAll(timeListeners)

	/**
	 * removes auto-executed time listeners specified in the given array.
	 *
	 * @param listeners the array
	 */
	infix fun removeTimeListeners(listeners: Array<FTimeListener>) = listeners.forEach { l -> removeTimeListener(l) }

	/**
	 * remove TimeListeners using vararg
	 */
	fun removeTimeListener(vararg listeners: FTimeListener) = listeners.forEach { l -> removeTimeListener(l) }

	/**
	 * auto-execute time listeners which are equal to the given collection.
	 *
	 * @param listeners the collection
	 */
	infix fun removeTimeListeners(listeners: Collection<FTimeListener>) = removeTimeListeners(listeners.toTypedArray())

	/**
	 * removes specified listener
	 *
	 * @param listener the listener
	 */
	infix fun removeTimeListener(listener: FTimeListener) = timeListenerDeleteBuffer.add(listener)


	/**
	 * do the delete and add work, to prevent Exceptions
	 */
	private fun processBuffer() {
		objects.addAll(objectAddBuffer)
		objects.removeAll(objectDeleteBuffer)
		objectDeleteBuffer.clear()
		objectAddBuffer.clear()

		timeListeners.addAll(timeListenerAddBuffer)
		timeListeners.removeAll(timeListenerDeleteBuffer)
		timeListenerDeleteBuffer.clear()
		timeListenerAddBuffer.clear()

		texts.addAll(textAddBuffer)
		texts.removeAll(textDeleteBuffer)
		textDeleteBuffer.clear()
		textAddBuffer.clear()
	}

	protected fun drawEverything(getBGG: () -> Graphics2D) {
		processBuffer()

		objects.forEach { o ->
			if (o is FObject) {
				o.runAnims()
				o.checkCollision()
			}
		}

		objects.forEach { o ->
			val bgg = getBGG()
			bgg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
			if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
			else bgg.rotate(o.rotate, o.x, o.y)
			when (o) {
				is FObject.ImageOwner ->
					unless((o.x + o.image.width).toInt() < 0 ||
							o.x.toInt() > width ||
							(o.y + o.image.height).toInt() < 0 ||
							o.y.toInt() > height) {

						bgg.drawImage(o.image, o.x.toInt(), o.y.toInt(), this)

					}
				is ShapeObject ->
					unless((o.x + o.width).toInt() < 0 ||
							o.x.toInt() > width ||
							(o.y + o.height).toInt() < 0 ||
							o.y.toInt() > height) {

						bgg.color = o.getResource().color
						when (o.collideBox) {
							is FRectangle -> bgg.fillRect(
									o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt()
							)
							is FOval -> bgg.fillOval(
									o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt()
							)
						}

					}
				is LineEffect -> bgg.drawLine(o.x.toInt(), o.y.toInt(), o.x2.toInt(), o.y2.toInt())
			}
			if (autoGC && (o.x.toInt() < -width ||
					o.x.toInt() > width + width ||
					o.y.toInt() < -height ||
					o.y.toInt() > height + height)) {
				if (o is PhysicalObject) o.died = true
				removeObject(o)
				//FLog.i("o.x.toInt() = ${o.x.toInt()}, width = $width," +
				//		" o.y.toInt() = ${o.y.toInt()}, height = $height")
			}
		}

		texts.forEach {
			b ->
			val bgg = getBGG()
			bgg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
			bgg.rotate(b.rotate)
			if (b is FButton) {
				when (b) {
					is FObject.ImageOwner ->
						unless((b.x + b.image.width).toInt() < 0 ||
								b.x.toInt() > width ||
								(b.y + b.image.height).toInt() < 0 ||
								b.y.toInt() > height) {

							bgg.drawImage(b.image, b.x.toInt(), b.y.toInt(), this)

						}
					is SimpleButton -> {
						bgg.color = b.getColor().color
						bgg.fillRoundRect(b.x.toInt(), b.y.toInt(),
								b.width.toInt(), b.height.toInt(),
								Math.min((b.width * 0.5).toInt(), 10),
								Math.min((b.height * 0.5).toInt(), 10))
						bgg.color = ColorResource.DARK_GRAY.color
						bgg.drawString(b.text, b.x.toInt() + 10, (b.y + (b.height / 2)).toInt())
					}
				}
			} else bgg.drawString(b.text, b.x.toInt(), b.y.toInt())
		}
		customDraw(getBGG())
	}

	/**
	 * set the frame bounds (size and position)
	 */
	override infix fun setBounds(r: Rectangle) {
		super.setBounds(r)
		panel.bounds = r
	}

	/**
	 * set the frame bounds (size and position)
	 */
	override fun setBounds(x: Int, y: Int, width: Int, height: Int) {
		super.setBounds(x, y, width, height)
		panel.setBounds(x, y, width, height)
	}

	/**
	 * set the frame size
	 */
	override fun setSize(width: Int, height: Int) {
		super.setSize(width, height)
		panel.setSize(width, height)
	}

	/**
	 * set the frame size
	 */
	override infix fun setSize(d: Dimension) {
		super.setSize(d)
		panel.size = d
	}

	/**
	 * get a screenShot.
	 *
	 * @return screen cut as an image
	 */
	fun getScreenCut() = ImageResource.create(stableBuffer)

	/**
	 * this method escaped the error
	 *
	 * @return exact position of the mouse
	 */
	override fun getMousePosition() = panel.mousePosition!!

	private fun drawBackground(back: FResource, g: Graphics2D) {
		when (back) {
			is ImageResource -> g.paint = TexturePaint(back.image, Rectangle(0, 0, width, height))
			is ColorResource -> g.color = back.color
			else -> throw FatalError("Unable to draw background")
		}
		g.fillRect(0, 0, width, height)
	}

	/**
	 * Demo24 game view.
	 * all rendering work and game object calculating are here.
	 *
	 * Created by ice1000 on 2016/8/13.
	 * @author ice1000
	 * @since v0.1
	 */
	inner class GamePanel() : JPanel() {
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
					onFocus(OnWindowEvent.create(e))
				}

				override fun windowDeactivated(e: WindowEvent) {
					loseFocus = true
					Clock.pause()
					onLoseFocus(OnWindowEvent.create(e))
				}

				override fun windowIconified(e: WindowEvent) = Unit
				override fun windowClosing(e: WindowEvent) = onExit()
				override fun windowClosed(e: WindowEvent) = Unit
				override fun windowOpened(e: WindowEvent) = Unit
			})
		}

		override fun update(g: Graphics?) = paint(g)
		override fun paintComponent(g: Graphics) {
			drawBackground(back, bg)
			drawEverything({ bg })

			if (loseFocus && loseFocusChangeColor) {
				loop(buffer.width) { x ->
					loop(buffer.height) { y ->
						buffer.setRGB(x, y, ColorUtils.darkerRGB(buffer.getRGB(x, y)))
					}
				}
			}

			if (showFPS) bg.drawString("fps: $fpsDisplay", 30, height - 30)

			/*
			 * 厚颜无耻
			 * bg.drawString("Powered by FriceEngine. ice1000", 5, 20)
			 */

			stableBuffer.graphics.drawImage(buffer, 0, 0, this)
			g.drawImage(buffer, 0, 0, this)
		}
	}

}