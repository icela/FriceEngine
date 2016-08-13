package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.resource.ImageResource
import org.frice.game.spirit.FObject
import org.frice.game.spirit.ImageObject
import java.awt.*
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
	protected var paused = false
	private val objs = ArrayList<FObject>()
	protected var backgroundColor: Color
		get() = panel.background
		set(value) {
			if (value is ImageResource) {
				buffer.graphics.drawImage(value.image.getScaledInstance(width, height, 0), 0, 0, this)
			} else panel.background = value
		}
	private val buffer: BufferedImage

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
			panel.repaint()
			Thread.sleep(200)
		}
	}

	protected fun addObject(obj: ImageObject) {
		buffer.graphics.drawImage(obj.getImage(), obj.x, obj.y, this)
		objs.add(obj)
	}

	protected fun removeObject(obj: ImageObject) {
		objs.remove(obj)
		//
		objs.forEach { obj ->
			when (obj) {
				is ImageObject -> buffer.graphics.drawImage(obj.getImage(), obj.x, obj.y, this)
				else -> {
				}
			}
		}
	}

	abstract fun onInit()
	abstract fun onExit()
	abstract fun onRefresh()
	abstract fun onClick(e: OnClickEvent)
	abstract fun onMouse(e: OnMouseEvent)

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
