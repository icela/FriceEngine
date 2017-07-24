package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.obj.FContainer

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface FButton : FContainer, AbstractObject {
	var onClickListener: (OnClickEvent) -> Unit

	infix fun onClick(e: OnClickEvent) = onClickListener(e)

	/** @return true means pressed */
	infix fun onMouse(e: OnMouseEvent) = (e.type == OnMouseEvent.MOUSE_PRESSED)
}
