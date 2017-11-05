package org.frice

import org.frice.obj.sub.DebugImageObject
import org.frice.resource.image.ImageResource

fun main(vararg args: String) {
	launch(object : Game() {
		val dbg = DebugImageObject(
				ImageResource.fromWeb("https://coding.net/u/ice1000/p/Images/git/raw/master/blog-img/13/a.png"), 10.0, 10.0)

		override fun onLastInit() {
			addObject(dbg)
			dbg.debugMove(20.0, 20.0)
		}
	})
}
