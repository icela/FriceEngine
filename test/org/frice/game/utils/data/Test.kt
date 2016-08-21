package org.frice.game.utils.data

import org.frice.game.Game
import org.frice.game.anim.move.AccelerateMove
import org.frice.game.anim.move.SimpleMove
import org.frice.game.anim.scale.SimpleScale
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.button.OnClickListener
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.graphics.shape.FCircle
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.message.log.FLog

/**
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */
class Test() : Game() {
	override fun onInit() {
		super.onInit()
		addObject(SimpleButton(FRectangle(80, 20), "I am a button", 30.0, 30.0, 80.0, 30.0).apply {
			onClickListener = object : OnClickListener {
				override fun onClick() {
					addObject(ShapeObject(ColorResource.西木野真姬, FCircle(40.0), 100.0, 100.0).apply {
						anims.add(SimpleMove(150, 150))
						anims.add(AccelerateMove(-1.0, -1.0))
						anims.add(SimpleScale(1.1, 1.0))
					})
				}
			}
		})
	}

	override fun onClick(e: OnClickEvent?) {
		super.onClick(e)
		FLog.v(e.toString())
		FLog.v(mousePosition)
	}

	override fun onExit() {
		System.exit(0)
	}
}

fun main(args: Array<String>) {
	Test()
}