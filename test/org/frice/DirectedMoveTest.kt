package org.frice

import org.frice.anim.move.DirectedMove
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FOval
import org.frice.utils.shape.FRectangle

class DirectedMoveTest : GameFX() {
	override fun onLastInit() {
		val a = ShapeObject(ColorResource.天依蓝, FRectangle(20, 20), 200.0, 200.0)
		val b = ShapeObject(ColorResource.八云紫, FOval(20.0, 20.0), 40.0, 40.0)
		a.addAnim(DirectedMove(a, b.x, b.y, 30.0))
		addObject(a, b)
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(DirectedMoveTest::class.java)
		}
	}
}
