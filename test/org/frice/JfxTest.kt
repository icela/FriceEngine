package org.frice

import org.frice.anim.move.SimpleMove
import org.frice.obj.button.SimpleText
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FCircle
import org.frice.utils.shape.FRectangle
import org.frice.utils.time.FTimer
import kotlin.test.assertEquals

fun main(args: Array<String>) {
	val obj = O()
	launch(obj)
}

class P : GameFX(width = 600, height = 600)

class O : GameFX() {
	val timer = FTimer(200)
	lateinit var obj: ShapeObject
	lateinit var obj2: ShapeObject

	@org.junit.Test
	override fun onInit() {
		obj2 = ShapeObject(ColorResource.天依蓝, FRectangle(20, 20), 200.0, 200.0, 233)
		obj = ShapeObject(ColorResource.西木野真姬, FCircle(30.0), 100.0, 100.0, 233).apply {
			mass = 1.0
			addAnim(SimpleMove(80, 0))
		}
		val text = SimpleText(ColorResource.BLUE, "this is a text demo", 100.0, 300.0)
		text.textSize = 64.0
		assertEquals(obj, obj2)
		addObject(obj2, obj, text)
	}

	override fun onRefresh() {
		if (timer.ended()) {
		}
	}

	override fun onLastInit() {
		// title = "Your awesome title"
		// not allowed
	}
}
