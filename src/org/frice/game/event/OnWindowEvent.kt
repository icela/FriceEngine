package org.frice.game.event

import java.awt.event.WindowEvent

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