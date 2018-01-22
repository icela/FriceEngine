package org.frice.anim.move

import org.frice.Game
import org.frice.launch
import org.frice.obj.sub.ImageObject
import org.frice.resource.image.ImageResource
import java.awt.Dimension

class ChasingMoveTest : Game() {
	override fun onLastInit() {
		size = Dimension(300, 300)
		addObject(ImageObject(ImageResource.fromPath("res/icon.png")).apply { addAnim(ChasingMove(this, mouse, 150.0)) })
	}
	companion object { @JvmStatic fun main(args: Array<String>) { launch(ChasingMoveTest::class.java) } }
}
