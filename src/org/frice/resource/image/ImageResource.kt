package org.frice.resource.image

import org.frice.Game
import org.frice.platform.FriceImage
import org.frice.platform.adapter.JvmImage
import org.frice.resource.FResource
import org.frice.resource.manager.ImageManager
import org.frice.resource.manager.WebImageManager
import org.frice.utils.message.FLog
import org.frice.utils.time.Clock
import org.frice.utils.time.FTimeListener
import java.awt.Rectangle

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class ImageResource : org.frice.resource.FResource {

	companion object Factories {
		@JvmStatic
		fun create(image: org.frice.platform.FriceImage) = object : org.frice.resource.image.ImageResource() {
			override var image = image
		}

		operator fun invoke(image: org.frice.platform.FriceImage) = org.frice.resource.image.ImageResource.Factories.create(image)

		@JvmStatic
		fun fromImage(image: org.frice.platform.FriceImage): org.frice.resource.image.ImageResource = org.frice.resource.image.ImageResource.Factories.create(image)

		@JvmStatic
		fun fromPath(path: String) = org.frice.resource.image.FileImageResource(path)

		@JvmStatic
		fun fromWeb(url: String) = org.frice.resource.image.WebImageResource(url)

		@JvmStatic
		fun empty() = org.frice.resource.image.ImageResource.Factories.create(org.frice.platform.adapter.JvmImage(1, 1))
	}

	abstract var image: org.frice.platform.FriceImage

	override fun getResource() = image

	fun scaled(x: Double, y: Double) = org.frice.resource.image.ImageResource.Factories.fromImage(image.getScaledInstance(
			image.width * x, image.height * y))

	fun part(x: Int, y: Int, width: Int, height: Int) = org.frice.resource.image.ImageResource.Factories.fromImage(image.getSubImage(x, y, width, height))
}

/**
 * create an image from a part of another image
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
class PartImageResource(origin: org.frice.resource.image.ImageResource, x: Int, y: Int, width: Int, height: Int) : org.frice.resource.image.ImageResource() {
	constructor(origin: org.frice.resource.image.ImageResource, part: Rectangle) : this(origin, part.x, part.y, part.width, part.height)

	override var image: org.frice.platform.FriceImage = origin.image.getSubImage(x, y, width, height)
}

/**
 * Image Resource from internet
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class WebImageResource(url: String) : org.frice.resource.image.ImageResource() {
	override var image: org.frice.platform.FriceImage = org.frice.resource.manager.WebImageManager[url]
}

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class FrameImageResource(
		val game: org.frice.Game,
		val list: MutableList<org.frice.resource.image.ImageResource>,
		div: Int) : org.frice.resource.image.ImageResource() {

	constructor(game: org.frice.Game, list: Array<org.frice.resource.image.ImageResource>, div: Int) : this(game, list.toMutableList(), div)

	private var start = Clock.current
	private val timer: FTimeListener
	private var counter = 0
	private var ended = false
	var loop = true

	override var image: org.frice.platform.FriceImage
		get() = if (loop || ended) list.getImage(counter).image else list.last().image
		set(value) {
			list.add(org.frice.resource.image.ImageResource(value))
		}

	fun MutableList<org.frice.resource.image.ImageResource>.getImage(index: Int): org.frice.resource.image.ImageResource {
		if (index == this.size - 1) ended = true
		return this[index]
	}

	init {
		timer = FTimeListener(div) {
			FLog.e("counter = $counter")
			counter = (counter + 1) % list.size
		}
		addTimeListener(timer)
	}
}

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class FileImageResource(file: String) : org.frice.resource.image.ImageResource() {
	override var image = org.frice.resource.manager.ImageManager[file]
}