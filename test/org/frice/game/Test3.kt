package org.frice.game

import org.frice.game.anim.move.CircumAnim
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.graphics.shape.FCircle

/**
 * Created by ice1000 on 2016/10/22.
 *
 * @author ice1000
 */

class Test3() : Game() {
	override fun onInit() {
		addObject(ShapeObject(ColorResource.BLUE, FCircle(50.0), 100.0, 100.0).apply {
			anims.add(CircumAnim(10.0, 10.0, 1.0))
		})
	}

	override fun onExit() {
		System.exit(0)
	}
}

fun main(args: Array<String>) {
	Test3()
}