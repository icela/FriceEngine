package org.frice.obj.button

import org.frice.event.OnClickEvent
import org.frice.event.OnMouseEvent
import org.frice.obj.AbstractObject
import org.frice.obj.FContainer
import java.util.function.Consumer

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface FButton : FContainer, AbstractObject {
	var onClickListener: Consumer<OnClickEvent>

	infix fun onClick(e: OnClickEvent) = onClickListener.accept(e)

	/** @return true means pressed */
	infix fun onMouse(e: OnMouseEvent) = (e.type == OnMouseEvent.MOUSE_PRESSED)
}
