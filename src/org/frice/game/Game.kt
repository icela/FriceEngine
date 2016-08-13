package org.frice.game

import org.frice.event.OnFrameClickEvent
import org.frice.event.OnFrameMouseEvent
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JPanel

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class Game() : Frame() {
	val panel = GamePanel()

	init {
		layout = BorderLayout()
		bounds = Rectangle(200, 200, 640, 480)
		addMouseListener(object : MouseListener {
			override fun mouseClicked(e: MouseEvent) = onClick(OnFrameClickEvent.create(e))
			override fun mouseEntered(e: MouseEvent) = onMouse(OnFrameMouseEvent.create(e))
			override fun mouseReleased(e: MouseEvent) = onMouse(OnFrameMouseEvent.create(e))
			override fun mouseExited(e: MouseEvent) = onMouse(OnFrameMouseEvent.create(e))
			override fun mousePressed(e: MouseEvent) = onMouse(OnFrameMouseEvent.create(e))
		})
		add(panel, BorderLayout.CENTER)
		onInit()
		isVisible = true
	}

	override fun setBounds(r: Rectangle) {
		super.setBounds(r)
		panel.bounds = r
	}

	/**
	 * Created by ice1000 on 2016/8/13.
	 * @author ice1000
	 * @since v0.1
	 */
	inner class GamePanel : JPanel() {
		init {
		}

		override fun paint(g: Graphics) {
			onPaint(g as Graphics2D)
		}
	}

	abstract fun onInit()
	abstract fun onExit()
	abstract fun onPaint(g: Graphics2D)
	abstract fun onClick(e: OnFrameClickEvent)
	abstract fun onMouse(e: OnFrameMouseEvent)

}