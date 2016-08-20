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
 * Do not override the constructor.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class Game() : AbstractGame(), Runnable {
	private val refresh = FTimer(4)

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

	protected var loseFocus = false
		private set

	init {
		isResizable = false
		add(panel, BorderLayout.CENTER)
		setBounds(200, 200, 640, 480)
		onInit()
		buffer = BufferedImage(panel.width, panel.height, BufferedImage.TYPE_INT_ARGB)
		stableBuffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		Thread(this).start()
		isVisible = true
		insets.set(0, insets.left, insets.bottom, insets.right)
		FLog.v("Engine start!")
	}

	fun addObjects(objs: Collection<AbstractObject>) = addObjects(objs.toTypedArray())
	fun addObjects(objs: Array<AbstractObject>) = objs.forEach { o -> addObject(o) }
	fun addObject(obj: AbstractObject) {
		if (obj is FText) texts.add(obj)
		else objects.add(obj)
	}

	protected fun clearObjects() = objectsDelete.addAll(objects)
	protected fun removeObjects(objs: Array<AbstractObject>) = objs.forEach { o -> objectsDelete.add(o) }
	protected fun removeObjects(objs: Collection<AbstractObject>) = removeObjects(objs.toTypedArray())
	protected fun removeObject(obj: AbstractObject) {
		if (obj is FText) textDelete.add(obj)
		else objectsDelete.add(obj)
	}

	fun addTimeListener(listener: FTimeListener) = timeListeners.add(listener)
	fun addTimeListeners(listeners: Array<FTimeListener>) = listeners.forEach { l -> addTimeListener(l) }
	fun addTimeListeners(listeners: Collection<FTimeListener>) = addTimeListeners(listeners.toTypedArray())

	protected fun clearTimeListeners() = timeListenersDelete.addAll(timeListeners)
	protected fun removeTimeListeners(listeners: Array<FTimeListener>) = listeners.forEach { l -> removeTimeListener(l) }
	protected fun removeTimeListeners(listeners: Collection<FTimeListener>) = removeTimeListeners(listeners.toTypedArray())
	protected fun removeTimeListener(listener: FTimeListener) = timeListenersDelete.add(listener)

	override fun touch(e: OnMouseEvent) = texts.forEach { b -> if (b is FButton) b.onClick(e) }

	override fun setBounds(r: Rectangle) {
		super.setBounds(r)
		panel.bounds = r
	}

	override fun setBounds(x: Int, y: Int, width: Int, height: Int) {
		super.setBounds(x, y, width, height)
		panel.setBounds(x, y, width, height)
	}

	override fun setSize(width: Int, height: Int) {
		super.setSize(width, height)
		panel.setSize(width, height)
	}

	override fun setSize(d: Dimension) {
		super.setSize(d)
		panel.size = d
	}

//	override fun getWidth() = panel.width
//	override fun getHeight() = panel.height

	protected fun getScreenCut() = ImageResource.create(stableBuffer)

	override fun getMousePosition() = panel.mousePosition!!

	override fun run() {
		loopIf({ !paused && !stopped && refresh.ended() }) {
			forceRun { onRefresh() }
			timeListeners.forEach { it.check() }
			panel.repaint()
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
					is FLine -> bg.drawLine(o.x.toInt(), o.y.toInt(), o.x2.toInt(), o.y2.toInt())
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
						buffer.setRGB(x, y, buffer.getRGB(x, y) * 2 / 3)
					}
				}
			}

			/**
			 * 厚颜无耻
			 */
			//bg.drawString("Powered by FriceEngine. ice1000", 5, 20)

			stableBuffer.graphics.drawImage(buffer, 0, 0, this)
			g.drawImage(buffer, 0, 0, this)
		}
	}
}
