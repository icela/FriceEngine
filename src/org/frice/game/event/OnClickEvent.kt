package org.frice.game.event

import java.awt.event.MouseEvent

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