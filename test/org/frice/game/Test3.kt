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
	val d = 3.0
	val c = 0.2
	override fun onInit() {
		setSize(1000, 1000)
		a = ShapeObject(ColorResource.BLUE, FCircle(50.0), 50.0, 500.0)
		a.anims.add(object : CustomMove() {
			override fun getXDelta(timeFromBegin: Double) =
					((a.x * c) * Math.sin(d) - (a.y * c) * Math.cos(d)) / 100

			override fun getYDelta(timeFromBegin: Double) =
					((a.x * c) * Math.cos(d) - (a.y * c) * Math.sin(d)) / 100
		})
		addObject(a)
	}

	override fun onExit() = System.exit(0)
}

fun main(args: Array<String>) {
	Test3()
}
