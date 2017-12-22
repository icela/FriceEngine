package org.frice.anim.rotate

import org.frice.GameFX
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FOval
import org.frice.utils.shape.FRectangle

class JavaFxRotateTest : GameFX(width = 200, height = 200) {
	override fun onLastInit() {
		addObject(
			ShapeObject(ColorResource.西木野取款姬, FRectangle(10.0, 10.0), 0.0, 0.0).apply { rotate(10.0) },
			ShapeObject(ColorResource.西木野取款姬, FOval(20.0, 40.0), 80.0, 60.0).apply { addAnim(AccelerateRotate(-10.0)) },
			ShapeObject(ColorResource.八云蓝, FOval(20.0, 40.0), 80.0, 60.0).apply { addAnim(AccelerateRotate(10.0)) })
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) = launch(JavaFxRotateTest::class.java)
	}
}