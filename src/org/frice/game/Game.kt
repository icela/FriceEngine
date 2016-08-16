package org.frice.game

import org.frice.game.event.OnWindowEvent
import org.frice.game.obj.FObject
import org.frice.game.obj.ImageObject
import org.frice.game.obj.ShapeObject
import org.frice.game.resource.ColorResource
import org.frice.game.resource.FResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.message.error.FatalError
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.shape.FOval
import org.frice.game.utils.shape.FRectangle
import org.frice.game.utils.time.FTimeListener
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Rectangle
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
abstract class Game() : AbstractGame(), Runnable {
	private val panel = GamePanel()
	private val objects = ArrayList<FObject>()
	private val timeListeners = ArrayList<FTimeListener>()
	private val buffer: BufferedImage
	private val bg: Graphics
		get() = buffer.graphics

	init {
		setBounds(200, 200, 640, 480)
		add(panel, BorderLayout.CENTER)
		addWindowListener(object : WindowListener {
			override fun windowDeiconified(e: WindowEvent) = Unit
			override fun windowActivated(e: WindowEvent) = onFocus(OnWindowEvent.create(e))
			override fun windowDeactivated(e: WindowEvent) = onLoseFocus(OnWindowEvent.create(e))
			override fun windowIconified(e: WindowEvent) = Unit
			override fun windowClosing(e: WindowEvent) = onExit()
			override fun windowClosed(e: WindowEvent) = System.exit(0)
			override fun windowOpened(e: WindowEvent) = Unit
		})
		onInit()
		buffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		isVisible = true
		Thread(this).start()
		FLog.v("Engine start!")
	}


	protected fun addObject(obj: FObject) = objects.add(obj)
	protected fun addObjects(objs: Array<FObject>) = objects.addAll(objs)
	protected fun clearObjects() = objects.clear()
	protected fun removeObject(obj: FObject) = objects.remove(obj)
	protected fun removeObjects(objs: Array<FObject>) = objects.removeAll(objs)
	protected fun addTimeListener(listener: FTimeListener) = timeListeners.add(listener)
	protected fun removeTimeListener(listener: FTimeListener) = timeListeners.remove(listener)

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

	override fun setSize(d: Dimension?) {
		super.setSize(d)
		panel.size = d
	}

	override fun run() {
		while (true) {
			if (!paused && !stopped) {
				try {
					onRefresh()
				} catch (e: Exception) {
					FLog.error(e)
				}
				timeListeners.forEach { it.check() }
				panel.repaint()
			}
			Thread.sleep((1000.0 / refreshPerSecond).toLong())
		}
	}

	private fun drawBackground(back: FResource) {
		when (back) {
			is ImageResource -> bg.drawImage(back.image.getScaledInstance(width, height, 0), 0, 0, this)
			is ColorResource -> {
				val g = bg
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
	inner class GamePanel : JPanel() {
		override fun update(g: Graphics?) = paint(g)
		override fun paint(g: Graphics) {
			drawBackground(back)
			objects.forEach { o ->
				o.runAnims()
				o.checkCollision()
				when (o) {
					is ImageObject -> bg.drawImage(o.getImage(), o.x.toInt(), o.y.toInt(), this)
					is ShapeObject -> {
						val bgg = bg
						bgg.color = o.res.color
						when (o.shape) {
							is FRectangle -> bgg.fillRect(o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt())
							is FOval -> bgg.fillOval(o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt())
						}
					}
				}
			}
			g.drawImage(buffer, 0, 0, this)
		}
	}
}
