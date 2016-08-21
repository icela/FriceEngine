package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.obj.FObject
import org.frice.game.obj.PhysicalObject
import org.frice.game.obj.button.FButton
import org.frice.game.obj.button.FText
import org.frice.game.obj.misc.FLine
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.FResource
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FPoint
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.graphics.utils.darker
import org.frice.game.utils.kotlin.forceRun
import org.frice.game.utils.kotlin.loop
import org.frice.game.utils.kotlin.loopIf
import org.frice.game.utils.message.error.FatalError
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JPanel

/**
 * The base game class.
 * this class do the render work, and something which are invisible to
 * game developer.
 *
 * Do not override the constructor.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class Game() : AbstractGame(), Runnable {
	private val refresh = FTimer(1)

	private val objects = ArrayList<AbstractObject>()
	private val objectsDelete = ArrayList<AbstractObject>()

	private val texts = ArrayList<FText>()
	private val textDelete = ArrayList<FText>()

	private val timeListeners = ArrayList<FTimeListener>()
	private val timeListenersDelete = ArrayList<FTimeListener>()

	private val buffer: BufferedImage

	private val panel = GamePanel()
	private val stableBuffer: BufferedImage
	private val bg: Graphics2D
		get() = buffer.graphics as Graphics2D

	private var fpsCounter = 0
	private var fpsDisplay = 0
	private val fpsTimer: FTimer

	protected var loseFocus = false
		private set

	init {
		add(panel, BorderLayout.CENTER)
		setBounds(200, 200, 640, 480)
		onInit()
		buffer = BufferedImage(panel.width, panel.height, BufferedImage.TYPE_INT_ARGB)
		stableBuffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		Thread(this).start()
		isVisible = true
		insets.set(0, insets.left, insets.bottom, insets.right)
		FLog.v("Engine start!")
		fpsTimer = FTimer(1000)
	}

	/**
	 * add objects
	 *
	 * @param objs as a collection
	 */
	fun addObjects(objs: Collection<AbstractObject>) = addObjects(objs.toTypedArray())

	/**
	 * add objects
	 *
	 * @param objs as an array
	 */
	fun addObjects(objs: Array<AbstractObject>) = objs.forEach { o -> addObject(o) }

	/**
	 * add an object to game, to be shown on screen.
	 */
	fun addObject(obj: AbstractObject) {
		if (obj is FText) texts.add(obj)
		else objects.add(obj)
	}

	/**
	 * clear all objects.
	 * this method is safe.
	 */
	protected fun clearObjects() = objectsDelete.addAll(objects)

	/**
	 * remove objects.
	 * this method is safe.
	 *
	 * @param objs will remove objects which is equal to them, as an array.
	 */
	protected fun removeObjects(objs: Array<AbstractObject>) = objs.forEach { o -> objectsDelete.add(o) }

	/**
	 * remove objects.
	 * this method is safe.
	 *
	 * @param objs will remove objects which is equal to them, as a collection.
	 */
	protected fun removeObjects(objs: Collection<AbstractObject>) = removeObjects(objs.toTypedArray())

	/**
	 * remove single object.
	 * this method is safe.
	 *
	 * @param objs will remove objects which is equal to it.
	 */
	protected fun removeObject(obj: AbstractObject) {
		if (obj is FText) textDelete.add(obj)
		else objectsDelete.add(obj)
	}

	/**
	 * add a auto-execute time listener
	 * you must add or it won't work.
	 */
	fun addTimeListener(listener: FTimeListener) = timeListeners.add(listener)

	/**
	 * add an array of auto-execute time listeners
	 */
	fun addTimeListeners(listeners: Array<FTimeListener>) = listeners.forEach { l -> addTimeListener(l) }

	/**
	 * add a collection of auto-execute time listeners
	 */
	fun addTimeListeners(listeners: Collection<FTimeListener>) = addTimeListeners(listeners.toTypedArray())

	/**
	 * remove all auto-execute time listeners
	 */
	protected fun clearTimeListeners() = timeListenersDelete.addAll(timeListeners)

	/**
	 * auto-execute time listeners which are equal to the given array.
	 *
	 * @param listeners the array
	 */
	protected fun removeTimeListeners(listeners: Array<FTimeListener>) =
			listeners.forEach { l -> removeTimeListener(l) }

	/**
	 * auto-execute time listeners which are equal to the given collection.
	 *
	 * @param listeners the collection
	 */
	protected fun removeTimeListeners(listeners: Collection<FTimeListener>) =
			removeTimeListeners(listeners.toTypedArray())

	/**
	 * remove a specific listener
	 *
	 * @param listener the listener
	 */
	protected fun removeTimeListener(listener: FTimeListener) = timeListenersDelete.add(listener)

	override fun touch(e: OnMouseEvent) = texts.forEach { b -> if (b is FButton) b.onClick(e) }

	/**
	 * set the frame bounds (size and position)
	 */
	override fun setBounds(r: Rectangle) {
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
	override fun setSize(d: Dimension) {
		super.setSize(d)
		panel.size = d
	}

//	override fun getWidth() = panel.width
//	override fun getHeight() = panel.height

	/**
	 * get the screen cut.
	 *
	 * @return screen cut as an image
	 */
	protected fun getScreenCut() = ImageResource.create(stableBuffer)

	/**
	 * this method escaped the error
	 *
	 * @return exact position of the mouse
	 */
	override fun getMousePosition() = panel.mousePosition!!

	override fun run() {
		loopIf({ !paused && !stopped && refresh.ended() }) {
			forceRun { onRefresh() }
			timeListeners.forEach { it.check() }
			panel.repaint()
			fpsCounter++
			forceRun {
				if (fpsTimer.ended()) {
					fpsDisplay = fpsCounter
					fpsCounter = 0
				}
			}
		}
	}

	private fun drawBackground(back: FResource, g: Graphics2D) {
		when (back) {
			is ImageResource -> g.paint = TexturePaint(back.image, Rectangle(0, 0, width, height))
			is ColorResource -> g.color = back.color
			else -> throw FatalError("Unable to draw background")
		}
		g.fillRect(0, 0, width, height)
	}

	/**
	 * Main game view.
	 * all rendering work and game object calculating are here.
	 *
	 * Created by ice1000 on 2016/8/13.
	 * @author ice1000
	 * @since v0.1
	 */
	inner class GamePanel() : JPanel() {
		init {
			addMouseListener(object : MouseListener {
				override fun mouseClicked(e: MouseEvent) = onClick(OnClickEvent.create(e))
				override fun mouseEntered(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_ENTERED))
				override fun mouseExited(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_EXITED))
				override fun mouseReleased(e: MouseEvent) {
					touch(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
					onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
				}

				override fun mousePressed(e: MouseEvent) {
					touch(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
					onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
				}
			})
			addWindowListener(object : WindowListener {
				override fun windowDeiconified(e: WindowEvent) = Unit
				override fun windowActivated(e: WindowEvent) {
					loseFocus = false
					onFocus(OnWindowEvent.create(e))
				}

				override fun windowDeactivated(e: WindowEvent) {
					loseFocus = true
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
			objects.removeAll(objectsDelete)
			objectsDelete.clear()

			timeListeners.removeAll(timeListenersDelete)
			timeListenersDelete.clear()

			texts.removeAll(textDelete)
			textDelete.clear()

			drawBackground(back, bg)
			objects.forEach { o ->
				if (o is FObject) {
					o.runAnims()
					o.checkCollision()
				}
			}
			objects.forEach { o ->
				val bgg = bg
				if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
				else bgg.rotate(o.rotate, o.x, o.y)
				when (o) {
					is ImageObject -> bgg.drawImage(o.getImage(), o.x.toInt(), o.y.toInt(), this)
					is ShapeObject -> {
						bgg.color = o.getResource().color
						bgg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON)
						when (o.collideBox) {
							is FPoint, is FRectangle -> bgg.fillRect(
									o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt())
							is FOval -> bgg.fillOval(
									o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt())
						}
					}
					is FLine -> {
						bgg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON)
						bgg.drawLine(o.x.toInt(), o.y.toInt(), o.x2.toInt(), o.y2.toInt())
					}
				}
				if (autoGC && (o.x.toInt() < -width || o.x.toInt() > width + width ||
						o.y.toInt() < -height || o.y.toInt() > height + height)) {
					if (o is PhysicalObject) o.died = true
					removeObject(o)
//					FLog.i("o.x.toInt() = ${o.x.toInt()}, width = $width," +
//							" o.y.toInt() = ${o.y.toInt()}, height = $height")
				}
			}
			texts.forEach { b ->
				val bgg = bg
				bgg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
				bgg.rotate(b.rotate)
				if (b is FButton) {
					bgg.color = b.getColor().color
					bgg.fillRoundRect(b.x.toInt(), b.y.toInt(),
							b.width.toInt(), b.height.toInt(),
							(b.width * 0.15).toInt(), (b.height * 0.15).toInt())
					bgg.color = ColorResource.GRAY.color
					bgg.drawString(b.text, b.x.toInt() + 10, b.y.toInt() + 20)
				} else bgg.drawString(b.text, b.x.toInt(), b.y.toInt())
			}
			if (loseFocus) {
				loop(buffer.width) { x ->
					loop(buffer.height) { y ->
						buffer.setRGB(x, y, buffer.getRGB(x, y).darker())
					}
				}
			}

			if (showFPS) bg.drawString("fps: $fpsDisplay", 30, height - 30)

			/**
			 * 厚颜无耻
			 */
			//bg.drawString("Powered by FriceEngine. ice1000", 5, 20)

			stableBuffer.graphics.drawImage(buffer, 0, 0, this)
			g.drawImage(buffer, 0, 0, this)
		}
	}
}
