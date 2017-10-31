package org.frice.obj.button

import org.frice.event.OnClickEvent
import org.frice.event.OnMouseEvent
import org.frice.obj.AbstractObject
import org.frice.obj.FContainer

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface FButton : org.frice.obj.FContainer, org.frice.obj.AbstractObject {
	var onClickListener: (org.frice.event.OnClickEvent) -> Unit

	infix fun onClick(e: org.frice.event.OnClickEvent) = onClickListener(e)

	/** @return true means pressed */
	infix fun onMouse(e: org.frice.event.OnMouseEvent) = (e.type == org.frice.event.OnMouseEvent.MOUSE_PRESSED)
}
