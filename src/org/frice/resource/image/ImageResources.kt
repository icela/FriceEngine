package org.frice.resource.image

import org.frice.platform.FriceImage
import org.frice.resource.manager.ImageManager
import org.frice.resource.manager.WebImageManager
import org.frice.utils.time.FTimer

/**
 * @author ice1000
 * @since v1.7.11
 */
open class ImageResourceImpl(override var image: FriceImage) : ImageResource()

/**
 * Image Resource from internet
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class WebImageResource(url: String) : ImageResourceImpl(WebImageManager[url])

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class FileImageResource(file: String) : ImageResourceImpl(ImageManager[file])

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class FrameImageResource(val list: MutableList<ImageResource>, div: Int) : ImageResource() {

	constructor(list: Array<ImageResource>, div: Int) : this(list.toMutableList(), div)

	private val timer = FTimer(div)
	private var counter = 0
	private var ended = false
	var loop = true

	override var image: FriceImage
		get() {
			if (timer.ended()) {
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
