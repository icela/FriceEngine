package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.resource.ColorResource
import org.frice.game.resource.FResource
import org.frice.game.resource.ImageResource
import org.frice.game.spirit.ColorObject
import org.frice.game.spirit.FObject
import org.frice.game.spirit.ImageObject
import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Rectangle
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JFrame

/**
 * Do not override the constructor.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class Game() : JFrame(), Runnable {
	private val panel = GamePanel()
	private val objects = ArrayList<FObject>()
	private val buffer: BufferedImage

	protected var paused = false
	protected var back: FResource = ColorResource.SHIT_YELLOW
	protected var refreshPerSecond = 10.0

	init {
		layout = BorderLayout()
		bounds = Rectangle(200, 200, 640, 480)
		addMouseListener(object : MouseListener {
			override fun mouseClicked(e: MouseEvent) = onClick(OnClickEvent.create(e))
			override fun mouseEntered(e: MouseEvent) = onMouse(OnMouseEvent.create(e))
			override fun mouseReleased(e: MouseEvent) = onMouse(OnMouseEvent.create(e))
			override fun mouseExited(e: MouseEvent) = onMouse(OnMouseEvent.create(e))
			override fun mousePressed(e: MouseEvent) = onMouse(OnMouseEvent.create(e))
		})
		add(panel, BorderLayout.CENTER)
		defaultCloseOperation = JFrame.EXIT_ON_CLOSE
		onInit()
		buffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		isVisible = true
		Thread(this).start()
	}

	override fun setBounds(r: Rectangle) {
		super.setBounds(r)
		panel.bounds = r
	}

	override fun run() {
		while (!paused) {
			onRefresh()
			drawBackground(back)
			objects.forEach { obj ->
				when (obj) {
					is ImageObject -> buffer.graphics.drawImage(obj.getImage(), obj.x, obj.y, this)
					is ColorObject -> {
						buffer.graphics.color = obj.res.color
						buffer.graphics.fillRect(0, 0, width, height)
					}
					else -> {
					}
				}
			}
			panel.repaint()
			Thread.sleep((1000 / refreshPerSecond).toLong())
		}
	}

	private fun drawBackground(back: FResource) {
		when(back) {
			is ImageResource -> buffer.graphics.drawImage(back.image.getScaledInstance(width, height, 0), 0, 0, this)
			is ColorResource -> {
				buffer.graphics.color = back.color
				buffer.graphics.fillRect(0, 0, width, height)
			}
		}
	}

	protected fun addObject(obj: FObject) = objects.add(obj)
	protected fun removeObject(obj: FObject) = objects.remove(obj)

	abstract fun onInit()
	abstract fun onExit()
	abstract fun onRefresh()
	abstract fun onClick(e: OnClickEvent?)
	abstract fun onMouse(e: OnMouseEvent?)

	/**
	 * Created by ice1000 on 2016/8/13.
	 * @author ice1000
	 * @since v0.1
	 */
	inner class GamePanel : Canvas() {
		override fun paint(g: Graphics) {
			g.drawImage(buffer, 0, 0, this)
		}
	}
}
