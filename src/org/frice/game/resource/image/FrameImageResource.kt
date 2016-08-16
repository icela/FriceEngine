package org.frice.game.resource.image

import org.frice.game.utils.time.FTimeListener
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class FrameImageResource(val list: MutableList<ImageResource>, val div: Int, val loop: Boolean) : ImageResource() {
	constructor(list: MutableList<ImageResource>, div: Int) : this(list, div, true)
	constructor(list: Array<ImageResource>, div: Int, loop: Boolean) : this(list.toMutableList(), div, loop)
	constructor(list: Array<ImageResource>, div: Int) : this(list.toMutableList(), div, true)

	private var start: Long
	private val timer: FTimeListener
	private var counter = 0

	override var image: BufferedImage
		get() = list[counter % list.size].image
		set(value) {
			list.add(ImageResource.create(value))
		}

	init {
		start = System.currentTimeMillis()
		timer = FTimeListener(div, { counter++ })
	}
}