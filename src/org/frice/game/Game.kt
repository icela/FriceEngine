package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.obj.FObject
import org.frice.game.obj.button.FButton
import org.frice.game.obj.effects.ParticleEffect
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.FResource
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.kotlin.forceRun
import org.frice.game.utils.kotlin.loopIf
import org.frice.game.utils.message.error.FatalError
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Rectangle
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
	private val refresh = FTimer(3)

	private val objects = ArrayList<AbstractObject>()
	private val objectsDelete = ArrayList<AbstractObject>()

	private val buttons = ArrayList<FButton>()
	private val buttonsDelete = ArrayList<FButton>()

	private val timeListeners = ArrayList<FTimeListener>()
	private val timeListenersDelete = ArrayList<FTimeListener>()

	private val buffer: BufferedImage

	private val panel = GamePanel()
	private val stableBuffer: BufferedImage
	private val bg: Graphics
		get() = buffer.graphics

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

	protected fun addObjects(objs: Array<AbstractObject>) = objs.forEach { o -> addObject(o) }
	protected fun addObject(obj: AbstractObject) {
		if (obj is FButton) buttons.add(obj)
		else objects.add(obj)
	}

	protected fun clearObjects() = objectsDelete.addAll(objects)
	protected fun removeObjects(objs: Array<AbstractObject>) = objs.forEach { o -> objectsDelete.add(o) }
	protected fun removeObject(obj: AbstractObject) {
		if (obj is FButton) buttonsDelete.add(obj)
		else objectsDelete.add(obj)
	}

	fun addTimeListener(listener: FTimeListener) = timeListeners.add(listener)
	fun addTimeListeners(listeners: Array<FTimeListener>) = listeners.forEach { l -> addTimeListener(l) }
	fun removeTimeListener(listener: FTimeListener) = timeListenersDelete.add(listener)
	fun removeTimeListeners(listeners: Array<FTimeListener>) = listeners.forEach { l -> removeTimeListener(l) }

	override fun touch(e: OnMouseEvent) = buttons.forEach { b -> b.onClick(e) }

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

	override fun run() {
		loopIf({ !paused && !stopped && refresh.ended() }) {
			forceRun { onRefresh() }
			timeListeners.forEach { it.check() }
			panel.repaint()
		}
	}

	private fun drawBackground(back: FResource, g: Graphics) {
		when (back) {
			is ImageResource -> g.drawImage(back.image.getScaledInstance(width, height, 0), 0, 0, this)
			is ColorResource -> {
				g.color = back.color
				g.fillRect(0, 0, width, height)
			}
			else -> throw FatalError("Unable to draw background")
		}
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
			drawBackground(back, bg)
			objects.forEach { o ->
				if (o is FObject) {
					o.runAnims()
					o.checkCollision()
				}
			}
			objects.forEach { o ->
				when (o) {
					is ParticleEffect ->
						bg.drawImage(o.getResource().getResource(), o.x.toInt(), o.y.toInt(), this)
					is ImageObject ->
						bg.drawImage(o.getImage(), o.x.toInt(), o.y.toInt(), this)
					is ShapeObject -> {
						val bgg = bg
						bgg.color = o.getResource().color
						when (o.collideBox) {
							is FRectangle -> bgg.fillRect(
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
				}
			}
			buttons.forEach { b ->
				val bgg = bg
				bgg.color = b.getColor().color
				bgg.fillRoundRect(b.x.toInt(), b.y.toInt(),
						b.width.toInt(), b.height.toInt(),
						(b.width * 0.15).toInt(), (b.height * 0.15).toInt())
			}
			objects.forEach { o ->
				if (autoGC && (o.x.toInt() < 0 || o.x.toInt() > width || o.y.toInt() < 0 || o.y.toInt() > height)) {
					removeObject(o)
//					FLog.i("o.x.toInt() = ${o.x.toInt()}, width = $width," +
//							" o.y.toInt() = ${o.y.toInt()}, height = $height")
				}
			}
			if (loseFocus) {
			}

			stableBuffer.graphics.drawImage(buffer, 0, 0, this)
			g.drawImage(buffer, 0, 0, this)

			objects.removeAll(objectsDelete)
			objectsDelete.clear()

			timeListeners.removeAll(timeListenersDelete)
			timeListenersDelete.clear()

			buttons.removeAll(buttonsDelete)
			buttonsDelete.clear()
		}
	}
}
