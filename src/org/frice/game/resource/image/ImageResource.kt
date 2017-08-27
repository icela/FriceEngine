package org.frice.game.resource.image

import org.frice.game.Game
import org.frice.game.platform.FriceImage
import org.frice.game.platform.adapter.JvmImage
import org.frice.game.resource.FResource
import org.frice.game.resource.manager.ImageManager
import org.frice.game.resource.manager.WebImageManager
import org.frice.game.utils.message.FLog
import org.frice.game.utils.time.Clock
import org.frice.game.utils.time.FTimeListener
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

	override fun getResource() = image

	fun scaled(x: Double, y: Double) = fromImage(image.getScaledInstance(
			image.width * x, image.height * y))

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

	private var start = Clock.current
	private val timer: FTimeListener
	private var counter = 0
	private var ended = false
	var loop = true

	override var image: FriceImage
		get() = if (loop || ended) list.getImage(counter).image else list.last().image
		set(value) {
			list.add(ImageResource(value))
		}

	fun MutableList<ImageResource>.getImage(index: Int): ImageResource {
		if (index == this.size - 1) ended = true
		return this[index]
	}

	init {
		timer = FTimeListener(div) {
			FLog.e("counter = $counter")
			counter = (counter + 1) % list.size
		}
		game.addTimeListener(timer)
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