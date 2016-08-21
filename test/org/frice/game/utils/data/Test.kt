package org.frice.game.utils.data

import org.frice.game.Game
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.graphics.shape.FCircle

/**
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */
class Test() : Game() {
	override fun onInit() {
		super.onInit()

//		FLog.i(FDialog(this).confirm("fuck"))
		addObject(ShapeObject(ColorResource.西木野真姬, FCircle(40.0), 100.0, 100.0).apply {
//			anims.add()
		})
	}

//	override fun onExit() {
//		System.exit(0)
//	}
}

fun main(args: Array<String>) {
	Test()
}