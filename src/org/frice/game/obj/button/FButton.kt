package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.FContainer

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface FButton : FContainer {
	var onClickListener: OnClickListener?

	fun onClick(e: OnClickEvent) {
		if (containsPoint(e.event.x, e.event.y)) onClickListener?.onClick(e)
	}

	fun onMouse(e: OnMouseEvent) = (e.type() == OnMouseEvent.MOUSE_PRESSED && containsPoint(e.event.x, e.event.y))

	/**
	 * Created by ice1000 on 2016/8/19.
	 * @author ice1000
	 * @since v0.4
	 */
	interface OnClickListener {
		fun onClick(e: OnClickEvent)
	}

	/**
	 * Created by ice1000 on 2016/8/19.
	 * @author ice1000
	 * @since v0.4
	 */
	interface OnMouseListener {
		fun onMouse(e: OnMouseEvent)
	}
}