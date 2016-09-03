package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.FContainer
import org.frice.game.resource.image.ImageResource

/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
class ImageButton(val imageNormal: ImageResource, val imagePressed: ImageResource,
                  override var x: Double, override var y: Double,
                  override var width: Double, override var height: Double) : FButton, FContainer {
	override fun onClick(e: OnClickEvent) {
	}

	override fun onMouse(e: OnMouseEvent) {
	}
}