package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnKeyEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.resource.FResource
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.message.FDialog
import java.awt.BorderLayout
import java.awt.Frame
import java.awt.Rectangle
import java.awt.event.*
import java.util.*
import javax.swing.JOptionPane

/**
 * First game class(not for you)
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
open class AbstractGame() : Frame() {
	companion object {
		@JvmStatic val SMALL_PHONE = Rectangle(100, 100, 480, 800)
		@JvmStatic val BIG_PHONE = Rectangle(100, 100, 720, 1200)
		@JvmStatic val HUGE_PHONE = Rectangle(100, 100, 1080, 1920)

		@JvmStatic val SMALL_SQUARE = Rectangle(100, 100, 400, 400)
		@JvmStatic val BIG_SQUARE = Rectangle(100, 100, 800, 800)

		@JvmStatic fun Rectangle.turn() {
			width -= -height
			height -= width
			width += height
		}
	}

	protected var paused = false
	protected var stopped = false
	protected var back: FResource = ColorResource.LIGHT_GRAY
	protected var refreshPerSecond = 1000
	protected var debug = true

	protected val random = Random()

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
		if (FDialog(this).confirm("Are you sure to exit?",
				"Ensuring",
				JOptionPane.YES_NO_OPTION) ==
				JOptionPane.YES_OPTION)
			System.exit(0)
	}

	protected open fun onLoseFocus(e: OnWindowEvent?) {
		paused = true
	}

	protected open fun onFocus(e: OnWindowEvent?) {
		paused = false
	}

	protected fun addKeyListener(
			typed: (KeyEvent) -> Unit = { },
			pressed: (KeyEvent) -> Unit = { },
			released: (KeyEvent) -> Unit = { }) {
		addKeyListener(object : KeyListener {
			override fun keyPressed(e: KeyEvent?) = pressed(e!!)
			override fun keyReleased(e: KeyEvent?) = released(e!!)
			override fun keyTyped(e: KeyEvent?) = typed(e!!)
		})
	}

	protected fun listenKeyPressed(key: OnKeyEvent) =
			addKeyListener({ key.execute(it) }, { key.execute(it) }, { key.execute(it) })
}