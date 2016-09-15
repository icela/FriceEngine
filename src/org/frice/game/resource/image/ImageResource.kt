package org.frice.game.resource.image

import org.frice.game.Game
import org.frice.game.resource.FResource
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.web.WebUtils
import java.awt.Image
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class ImageResource : FResource {

	companion object {
		@JvmStatic fun create(image: BufferedImage) = object : ImageResource() {
			override var image = image
		}

		@JvmStatic fun fromImage(image: BufferedImage): ImageResource = create(image)
		@JvmStatic fun fromFile(file: File) = FileImageResource(file)
		@JvmStatic fun fromPath(path: String) = FileImageResource(path)
		@JvmStatic fun fromWeb(url: String) = WebImageResource(url)
		@JvmStatic fun fromURL(url: URL) = WebImageResource(url)
		@JvmStatic fun empty() = create(BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB))
	}

	abstract var image: BufferedImage

	override fun getResource() = image

	fun scaled(x: Double, y: Double) = fromImage(image.getScaledInstance(
			(image.width * x).toInt(), (image.height * y).toInt(), Image.SCALE_DEFAULT) as BufferedImage)

	fun part(x: Int, y: Int, width: Int, height: Int) = fromImage(image.getSubimage(x, y, width, height))

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

	override var image = origin.image.getSubimage(x, y, width, height)
}


/**
 * Image Resource from internet
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class WebImageResource(url: URL) : ImageResource() {

	constructor(url: String) : this(URL(url))

	override var image = WebUtils.readImage(url)

}


/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class FrameImageResource(val game: Game, val list: MutableList<ImageResource>, div: Int) : ImageResource() {

	constructor(game: Game, list: Array<ImageResource>, div: Int) : this(game, list.toMutableList(), div)

	private var start: Long
	private val timer: FTimeListener
	private var counter = 0
	private var ended = false
	var loop = true

	override var image: BufferedImage
		get() = if (loop || ended) list.getImage(counter).image else list.last().image
		set(value) {
			list.add(ImageResource.create(value))
		}

	fun MutableList<ImageResource>.getImage(index: Int): ImageResource {
		if (index == this.size - 1) ended = true
		return this[index]
	}

	init {
		start = System.currentTimeMillis()
		timer = FTimeListener(div, {
			FLog.e("counter = $counter")
			counter++
			counter %= list.size
		})
		game.addTimeListener(timer)
	}
}

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class FileImageResource(file: File) : ImageResource() {

	constructor(path: String) : this(File(path))

	override var image = ImageIO.read(file)!!

}