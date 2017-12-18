package org.frice.platform.adapter

import org.frice.*
import org.frice.obj.sub.ImageObject
import org.frice.resource.image.ImageResource

class ImageTest : GameFX(width = 300, height = 300) {
	override fun onInit() {
		val image = ImageResource.fromPath("res/icon.png")
		addObject(ImageObject(image),
			ImageObject(image.flip(true), x = image.image.width.toDouble()),
			ImageObject(image.flip(false), y = image.image.height.toDouble()),
			ImageObject(image.flip(true).flip(false),
				x = image.image.width.toDouble(), y = image.image.height.toDouble()))
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launchFx(ImageTest::class.java)
		}
	}
}
