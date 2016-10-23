package org.frice.game

import org.frice.game.anim.move.CustomMove
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.graphics.shape.FCircle

/**
 * Created by ice1000 on 2016/10/22.
 *
 * @author ice1000
 */

class Test3() : Game() {
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
}

fun main(args: Array<String>) {
	Test3()
}
