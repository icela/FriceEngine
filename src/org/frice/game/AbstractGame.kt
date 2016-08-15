package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.resource.ColorResource
import org.frice.game.resource.FResource
import java.awt.BorderLayout
import java.awt.Frame
import java.awt.Rectangle
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

/**
 * First game class(not for you)
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
abstract class AbstractGame() : Frame() {
	protected var paused = false
	protected var back: FResource = ColorResource.SHIT_YELLOW
	protected var refreshPerSecond = 15
	protected var debug = true

	init {
		layout = BorderLayout()
		bounds = Rectangle(200, 200, 640, 480)
		addMouseListener(object : MouseListener {
			override fun mouseClicked(e: MouseEvent) = onClick(OnClickEvent.create(e))
			override fun mouseEntered(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_ENTERED))
			override fun mouseReleased(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
			override fun mouseExited(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_EXITED))
			override fun mousePressed(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
		})
	}

	protected abstract fun onInit()
	protected abstract fun onRefresh()
	protected abstract fun onClick(e: OnClickEvent?)
	protected abstract fun onMouse(e: OnMouseEvent?)
	protected open fun onExit() = System.exit(0)
	protected open fun onLoseFocus(e: OnWindowEvent?) {
		paused = true
	}

	protected open fun onFocus(e: OnWindowEvent?) {
		paused = false
	}

}