package org.frice.game.obj.button

import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.PhysicalObject

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface FButton : FText, PhysicalObject {
	fun onClick(e: OnMouseEvent)
}