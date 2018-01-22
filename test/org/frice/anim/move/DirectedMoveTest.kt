package org.frice.anim.move

import org.frice.Game
import org.frice.launch
import org.frice.obj.sub.ImageObject
import org.frice.resource.image.ImageResource
import java.awt.Dimension

class DirectedMoveTest : Game() {
	override fun onLastInit() {
		size = Dimension(300, 300)
	}

	override fun onRefresh() {
		addObject(ImageObject(ImageResource.fromPath("res/icon.png"), 150.0).apply { addAnim(DirectedMove(this, mouse.x, mouse.y, 150.0)) })
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(DirectedMoveTest::class.java)
		}
	}
}
