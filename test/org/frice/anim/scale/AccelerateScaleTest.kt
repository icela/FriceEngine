package org.frice.anim.scale

import org.frice.Game
import org.frice.launch
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FOval

class AccelerateScaleTest : Game() {
	override fun onInit() {
		setSize(200, 200)
		addObject(ShapeObject(ColorResource.八云紫, FOval(20.0, 40.0), 0.0, 0.0).apply { addAnim(AccelerateScale(1.1, 1.1)) })
	}
	companion object { @JvmStatic fun main(args: Array<String>) = launch(AccelerateScaleTest::class.java) }
}