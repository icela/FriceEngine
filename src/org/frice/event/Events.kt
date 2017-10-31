package org.frice.event

import java.awt.event.MouseEvent

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class OnClickEvent(val event: MouseEvent)

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class OnMouseEvent(e: MouseEvent, val type: Int) : org.frice.event.OnClickEvent(e) {
	companion object {
		@JvmField val MOUSE_CLICK = 0x00
		@JvmField val MOUSE_RELEASED = 0x01
		@JvmField val MOUSE_ENTERED = 0x02
		@JvmField val MOUSE_EXITED = 0x03
		@JvmField val MOUSE_PRESSED = 0x04
	}

	@Deprecated("Will be removed soon", replaceWith = ReplaceWith("getType()")) fun type() = type
}
