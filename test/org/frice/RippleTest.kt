package org.frice

import org.frice.obj.sub.ImageObject
import org.frice.resource.image.ImageResource
import org.frice.resource.image.RippledImageResource

fun main(args: Array<String>) {
	launch(object : Game() {
		override fun onInit() {
			val url = "./res/icon.png"
			addObject(ImageObject(RippledImageResource(ImageResource.fromPath(url).scaled(2.0, 2.0), 10).apply {
			}))
		}
	})
}