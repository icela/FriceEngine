package org.frice.game.event

import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.WindowEvent

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface OnClickEvent {

	companion object {
		fun create(e: MouseEvent) = object : OnClickEvent {
			override val event = e
		}
	}

	val event: MouseEvent
}


/**
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since v0.3.2
 */
interface OnKeyEvent {
	fun execute(e: KeyEvent)
}


/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface OnMouseEvent : OnClickEvent {
	companion object {

		val MOUSE_CLICK = 0x00
		val MOUSE_RELEASED = 0x01
		val MOUSE_ENTERED = 0x02
		val MOUSE_EXITED = 0x03
		val MOUSE_PRESSED = 0x04

		fun create(e: MouseEvent, t: Int) = object : OnMouseEvent {
			override val event = e
			override fun type() = t
		}
	}

	fun type(): Int
}


/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 */
interface OnWindowEvent {
	companion object {
		fun create(e: WindowEvent) = object : OnWindowEvent {
			override val event = e
		}
	}

	val event: WindowEvent
}