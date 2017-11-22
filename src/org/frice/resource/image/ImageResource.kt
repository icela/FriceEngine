package org.frice.resource.image

import org.frice.Game
import org.frice.anim.move.SimpleMove
import org.frice.platform.FriceImage
import org.frice.platform.adapter.JvmImage
import org.frice.resource.FResource
import org.frice.resource.manager.ImageManager
import org.frice.resource.manager.WebImageManager
import org.frice.utils.message.FLog
import org.frice.utils.misc.squared
import org.frice.utils.time.*
import java.awt.Rectangle

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
	}

	abstract var image: FriceImage
	override val resource get() = image

	fun scaled(x: Double, y: Double) = fromImage(image.getScaledInstance(x, y))

	fun part(x: Int, y: Int, width: Int, height: Int) = fromImage(image.getSubImage(x, y, width, height))
}

/**
 * create an image from a part of another image
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
class PartImageResource(origin: ImageResource, x: Int, y: Int, width: Int, height: Int) : ImageResource() {
	constructor(origin: ImageResource, part: Rectangle) : this(origin, part.x, part.y, part.width, part.height)

	override var image: FriceImage = origin.image.getSubImage(x, y, width, height)
}

/**
 * Image Resource from internet
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class WebImageResource(url: String) : ImageResource() {
	override var image: FriceImage = WebImageManager[url]
}

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class FrameImageResource(
		val game: Game,
		val list: MutableList<ImageResource>,
		div: Int) : ImageResource() {

	constructor(game: Game, list: Array<ImageResource>, div: Int) : this(game, list.toMutableList(), div)

	private val timer = FTimer(div)
	private var counter = 0
	private var ended = false
	var loop = true

	override var image: FriceImage
		get() {
			if (timer.ended()) {
				FLog.e("counter = $counter")
				counter = (counter + 1) % list.size
			}
			return if (loop || ended) list.getImage(counter).image else list.last().image
		}
		set(value) {
			list += ImageResource(value)
		}

	private fun MutableList<ImageResource>.getImage(index: Int): ImageResource {
		if (index == this.size - 1) ended = true
		return this[index]
	}
}

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class FileImageResource(file: String) : ImageResource() {
	override var image = ImageManager[file]
}

/**
 * Android Material Design's ripple effect
 * tmp given up
 *
 * @author ice1000
 */
class RippledImageResource(
		val base: ImageResource,
		speed: Int = 10) : ImageResource() {
	private var internalImage = base.image.clone()
	override var image: FriceImage
		get() {
			for (i in 0 until width)
				for (j in 0 until height)
					internalImage[i, j] = if ((i - x).squared() + (j - y).squared() > anim.delta.x.toInt().squared()) base.image[i, j] else base.image[i, j].darker()
			return internalImage
		}
		set(value) = Unit

	val width get() = internalImage.width
	val height get() = internalImage.height

	var x: Int = width / 2
	var y: Int = height / 2
	var speed: Int
		get() = anim.x
		set(value) {
			anim.run { x = value }
		}

	private var anim: SimpleMove = SimpleMove(speed, 0)

	fun reset(x: Int = this.x, y: Int = this.y) {
		this.x = x
		this.y = y
		anim = SimpleMove(speed, 0)
	}

	init {
		this.speed = speed
	}
}