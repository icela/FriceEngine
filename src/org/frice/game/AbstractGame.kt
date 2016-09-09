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
import org.frice.game.obj.effects.LineEffect
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.FResource
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FPoint
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.time.FTimeListener
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.WindowConstants

/**
 * First game class(not for you)
 *
 * Standard library, mainly for GUI.
 * some other library is in @see
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
abstract class AbstractGame() : JFrame() {
	companion object {
		@JvmStatic val TO_X = 100
		@JvmStatic val TO_Y = 100

		@JvmStatic val SMALL_PHONE = Rectangle(TO_X, TO_Y, 480, 800)
		@JvmStatic val BIG_PHONE = Rectangle(TO_X, TO_Y, 720, 1200)
		@JvmStatic val HUGE_PHONE = Rectangle(TO_X, TO_Y, 1080, 1920)

		@JvmStatic val SMALL_SQUARE = Rectangle(TO_X, TO_Y, 400, 400)
		@JvmStatic val BIG_SQUARE = Rectangle(TO_X, TO_Y, 800, 800)

		@JvmStatic fun Rectangle.turn() {
			width -= -height
			height -= width
			width += height
		}
	}

	/**
	 * if paused, main window will not call `onRefresh()`.
	 */
	var paused = false

	/**
	 * not implemented yet.
	 * currently it's same as paused.
	 */
	var stopped = false

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
		private set

	var loseFocusChangeColor = true

	protected val objects = LinkedList<AbstractObject>()
	protected val objectDeleteBuffer = ArrayList<AbstractObject>()
	protected val objectAddBuffer = ArrayList<AbstractObject>()

	protected val timeListeners = LinkedList<FTimeListener>()
	protected val timeListenerDeleteBuffer = ArrayList<FTimeListener>()
	protected val timeListenerAddBuffer = ArrayList<FTimeListener>()

	protected val texts = LinkedList<FText>()
	protected val textDeleteBuffer = ArrayList<FText>()
	protected val textAddBuffer = ArrayList<FText>()

	init {
		isResizable = false
		defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
		layout = BorderLayout()
	}

	protected fun mouse(e: OnMouseEvent) = texts.forEach { b ->
		if (b is FButton && b.containsPoint(e.event.x, e.event.y)) b onMouse e
	}

	protected fun click(e: OnClickEvent) = texts.forEach { b ->
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
	fun listenKeyPressed(key: (KeyEvent) -> Unit) =
			addKeyListener({ key.invoke(it) }, { key.invoke(it) }, { key.invoke(it) })

	fun addKeyTypedEvent(keyCode: Int, key: OnKeyEvent) = addKeyTypedEvent(keyCode, { e -> key.execute(e) })
	fun addKeyTypedEvent(keyCode: Int, key: (KeyEvent) -> Unit) = addKeyListener(typed = { e ->
		if (e.keyCode == keyCode) key.invoke(e)
	})

	fun addKeyPressedEvent(keyCode: Int, key: OnKeyEvent) =
			addKeyPressedEvent(keyCode, { e -> key.execute(e) })

	fun addKeyPressedEvent(keyCode: Int, key: (KeyEvent) -> Unit) = addKeyListener(pressed = { e ->
		if (e.keyCode == keyCode) key.invoke(e)
	})

	fun addKeyReleasedEvent(keyCode: Int, key: OnKeyEvent) =
			addKeyReleasedEvent(keyCode, { e -> key.execute(e) })

	fun addKeyReleasedEvent(keyCode: Int, key: (KeyEvent) -> Unit) = addKeyListener(released = { e ->
		if (e.keyCode == keyCode) key.invoke(e)
	})

	fun setCursor(o: ImageResource) = setCursor(ImageObject(o))
	fun setCursor(o: ImageObject) {
		cursor = toolkit.createCustomCursor(o.getImage(), Point(0, 0), "cursor")
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
	fun clearObjects() = objectDeleteBuffer.addAll(objects)

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
	 * adds a auto-executed time listener
	 * you must add or it won't work.
	 */
	infix fun addTimeListener(listener: FTimeListener) = timeListenerAddBuffer.add(listener)

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
	infix fun removeTimeListeners(listeners: Array<FTimeListener>) =
			listeners.forEach { l -> removeTimeListener(l) }

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

	protected fun drawEverything(bg: Graphics2D) {
		processBuffer()

		objects.forEach { o ->
			if (o is FObject) {
				o.runAnims()
				o.checkCollision()
			}
		}
		objects.forEach { o ->
			val bgg = bg
			bgg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
			if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
			else bgg.rotate(o.rotate, o.x, o.y)
			when (o) {
				is FObject.ImageOwner -> bgg.drawImage(o.getImage(), o.x.toInt(), o.y.toInt(), this)
				is ShapeObject -> {
					bgg.color = o.getResource().color
					when (o.collideBox) {
						is FPoint, is FRectangle -> bgg.fillRect(
								o.x.toInt(),
								o.y.toInt(),
								o.width.toInt(),
								o.height.toInt())
						is FOval -> bgg.fillOval(o.x.toInt(), o.y.toInt(), o.width.toInt(), o.height.toInt())
					}
				}
				is LineEffect -> bgg.drawLine(o.x.toInt(), o.y.toInt(), o.x2.toInt(), o.y2.toInt())
			}
			if (autoGC && (o.x.toInt() < -width || o.x.toInt() > width + width ||
					o.y.toInt() < -height || o.y.toInt() > height + height)) {
				if (o is PhysicalObject) o.died = true
				removeObject(o)
				//FLog.i("o.x.toInt() = ${o.x.toInt()}, width = $width," +
				//		" o.y.toInt() = ${o.y.toInt()}, height = $height")
			}
		}
		texts.forEach { b ->
			val bgg = bg
			bgg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
			bgg.rotate(b.rotate)
			if (b is FButton) {
				bgg.color = b.getColor().color
				bgg.fillRoundRect(b.x.toInt(), b.y.toInt(),
						b.width.toInt(), b.height.toInt(),
						(b.width * 0.3).toInt(), (b.height * 0.3).toInt())
				bgg.color = ColorResource.DARK_GRAY.color
				bgg.drawString(b.text, b.x.toInt() + 10, (b.y + (b.height / 2)).toInt())
			} else bgg.drawString(b.text, b.x.toInt(), b.y.toInt())
		}

		customDraw(bg)
	}

}