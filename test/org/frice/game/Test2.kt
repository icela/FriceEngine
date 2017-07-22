package org.frice.game

import org.frice.game.anim.move.CustomMove
import org.frice.game.anim.move.SimpleMove
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FCircle
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.time.FTimer
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by ice1000 on 2016/9/11.
 *
 * @author ice1000
 */


class Test2 : Game() {
	val timer = FTimer(200)
	lateinit var obj: ShapeObject
	lateinit var obj2: ShapeObject

	@Test
	override fun onInit() {
		obj2 = ShapeObject(ColorResource.Companion.天依蓝, FRectangle(20, 20), 200.0, 200.0, 233).apply {
			mass = 2.0
		}
		obj = ShapeObject(ColorResource.Companion.西木野真姬, FCircle(30.0), 100.0, 100.0, 233).apply {
			mass = 1.0
			anims.add(SimpleMove(80, 0))
		}
		assertEquals(obj, obj2)
		addObject(obj2, obj)
	}

	override fun onRefresh() {
		if (timer.ended()) {
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(Test2::class.java)
		}
	}
}


/**
 * Created by ice1000 on 2016/10/22.
 *
 * @author ice1000
 */
class Test3 : Game() {
	lateinit var a: ShapeObject
	val d = 3.14 * 6
	val e = 0.1
	val c = 0.1
	override fun onInit() {
		setSize(1000, 1000)
		a = ShapeObject(ColorResource.BLUE, FCircle(10.0), 100.0, 500.0)
		a.anims.add(object : CustomMove() {
			override fun getXDelta(timeFromBegin: Double) =
					((a.x * c) * Math.sin(d) - (a.y * c) * Math.cos(d)) * e

			override fun getYDelta(timeFromBegin: Double) =
					((a.x * c) * Math.cos(d) - (a.y * c) * Math.sin(d)) * e
		})
		addObject(a)
	}

	override fun onExit() = System.exit(0)

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(Test3::class.java)
		}
	}
}
