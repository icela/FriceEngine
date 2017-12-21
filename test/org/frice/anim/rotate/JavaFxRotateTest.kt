package org.frice.anim.rotate

import org.frice.GameFX
import org.frice.launch
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FOval

class JavaFxRotateTest : GameFX(width = 200, height = 200) {
	override fun onInit() {
		addObject(ShapeObject(ColorResource.八云紫, FOval(20.0, 40.0), 80.0, 60.0).apply { addAnim(AccelerateRotate(10.0)) })
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) = launch(AccelerateRotateTest::class.java)
	}
}