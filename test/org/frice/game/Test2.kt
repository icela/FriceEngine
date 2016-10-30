package org.frice.game

import org.frice.game.anim.move.SimpleMove
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FCircle
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.time.FTimer

/**
 * Created by ice1000 on 2016/9/11.
 *
 * @author ice1000
 */


class Test2() : Game() {
	val timer = FTimer(200)
	lateinit var obj: ShapeObject
	lateinit var obj2: ShapeObject

	override fun onInit() {
		obj2 = ShapeObject(ColorResource.Companion.天依蓝, FRectangle(20, 20), 200.0, 200.0).apply {
			mass = 2.0
		}
		obj = ShapeObject(ColorResource.Companion.西木野真姬, FCircle(30.0), 100.0, 100.0).apply {
			mass = 1.0
			anims.add(SimpleMove(80, 0))
		}
		addObject(obj2, obj, ImageObject(ImageResource.fromWeb("http://media.openjudge.cn/images/1251_1.jpg")))
	}

	override fun onRefresh() {
		if (timer.ended()) {
		}
	}
}

fun main(args: Array<String>) {
	Test2()
}