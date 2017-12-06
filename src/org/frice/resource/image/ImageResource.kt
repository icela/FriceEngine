package org.frice.resource.image

import org.frice.platform.FriceImage
import org.frice.platform.adapter.JfxImage
import org.frice.platform.adapter.JvmImage
import org.frice.resource.FResource

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class ImageResource : FResource {

	companion object Factories {
		@JvmStatic
		fun create(image: FriceImage) = object : ImageResource() {
			override var image = image
		}

		operator fun invoke(image: FriceImage) = create(image)

		@JvmStatic
		fun fromImage(image: FriceImage): ImageResource = create(image)

		@JvmStatic
		fun fromPath(path: String) = FileImageResource(path)

		@JvmStatic
		fun fromWeb(url: String) = WebImageResource(url)

		@JvmStatic
		fun empty() = create(JvmImage(1, 1))

		@JvmStatic
		fun emptyFx() = create(JfxImage(1, 1))
	}

	abstract var image: FriceImage
	override val resource get() = image

	fun scaled(x: Double, y: Double) = fromImage(image.getScaledInstance(x, y))

	fun part(x: Int, y: Int, width: Int, height: Int) = fromImage(image.getSubImage(x, y, width, height))
}