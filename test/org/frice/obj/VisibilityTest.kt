package org.frice.obj

import org.frice.Game
import org.frice.event.MOUSE_CLICKED
import org.frice.event.OnMouseEvent
import org.frice.launch
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.util.shape.FOval

class VisibilityTest : Game() {
	val obj = ShapeObject(ColorResource.八云紫, FOval(20.0, 40.0), 0.0, 0.0)

	override fun onLastInit() {
		setSize(200, 200)
		addObject(obj)
	}

	override fun onMouse(e: OnMouseEvent) {
		if (e.type == MOUSE_CLICKED) {
			obj.isVisible = !obj.isVisible
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(VisibilityTest::class.java)
		}
	}
}
