package org.frice.event

import java.awt.event.MouseEvent

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface OnFrameClickEvent {

	companion object {
		fun create(e: MouseEvent) = object : OnFrameClickEvent {
			override val event = e
		}
	}

	val event: MouseEvent
}