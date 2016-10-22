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
	val d = 15.0
	override fun onInit() {
		a = ShapeObject(ColorResource.BLUE, FCircle(50.0), 100.0, 100.0)
		a.anims.add(object : CustomMove() {
			override fun getXDelta(timeFromBegin: Double): Double {
				return (a.x * Math.sin(d) - a.y * Math.cos(d)) / 50
			}
			override fun getYDelta(timeFromBegin: Double): Double {
				return (a.x * Math.cos(d) - a.y * Math.sin(d)) / 50
			}
		})
		addObject(a)
	}

	override fun onExit() {
		System.exit(0)
	}
}

fun main(args: Array<String>) {
	Test3()
}
