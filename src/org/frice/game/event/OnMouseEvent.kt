package org.frice.game.event

import java.awt.event.MouseEvent

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface OnMouseEvent : OnClickEvent {
	companion object {

		val MOUSE_CLICK = 0x00
		val MOUSE_RELEASE = 0x01
		val MOUSE_ENTERED = 0x02
		val MOUSE_EXITED = 0x03
		val MOUSE_PRESSED = 0x04

		fun create(e: MouseEvent) = object : OnMouseEvent {
			override val event = e
		}
	}
}