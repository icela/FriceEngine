package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.obj.ImageObject
import org.frice.game.resource.ColorResource
import org.frice.game.resource.FResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.message.FDialog
import java.awt.BorderLayout
import java.awt.Frame
import java.awt.Point
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JOptionPane

/**
 * First game class(not for you)
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
open class AbstractGame() : Frame() {
	protected var paused = false
	protected var stopped = false
	protected var back: FResource = ColorResource.SHIT_YELLOW
	protected var refreshPerSecond = 30
	protected var debug = true

	init {
		layout = BorderLayout()
		addMouseListener(object : MouseListener {
			override fun mouseClicked(e: MouseEvent) = onClick(OnClickEvent.create(e))
			override fun mouseEntered(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_ENTERED))
			override fun mouseReleased(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
			override fun mouseExited(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_EXITED))
			override fun mousePressed(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
		})
		addWindowListener(object : WindowListener {
			override fun windowDeiconified(e: WindowEvent) = Unit
			override fun windowActivated(e: WindowEvent) = onFocus(OnWindowEvent.create(e))
			override fun windowDeactivated(e: WindowEvent) = onLoseFocus(OnWindowEvent.create(e))
			override fun windowIconified(e: WindowEvent) = Unit
			override fun windowClosing(e: WindowEvent) = onExit()
			override fun windowClosed(e: WindowEvent) = Unit
			override fun windowOpened(e: WindowEvent) = Unit
		})
	}

	protected open fun onInit() = Unit
	protected open fun onRefresh() = Unit
	protected open fun onClick(e: OnClickEvent?) = Unit
	protected open fun onMouse(e: OnMouseEvent?) = Unit
	protected open fun onExit() {
		if (FDialog(this).confirm("Are you sure to exit?", "Ensuring", JOptionPane.YES_NO_OPTION) ==
				JOptionPane.YES_OPTION)
			System.exit(0)
	}

	protected open fun onLoseFocus(e: OnWindowEvent?) {
		paused = true
	}

	protected open fun onFocus(e: OnWindowEvent?) {
		paused = false
	}


	protected fun setCursor(o: ImageObject) = setCursor(o.getResource())
	protected fun setCursor(o: ImageResource) {
		cursor = toolkit.createCustomCursor(o.image, Point(0, 0), "cursor")
	}

}