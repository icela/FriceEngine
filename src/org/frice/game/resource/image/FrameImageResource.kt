package org.frice.game.resource.image

import org.frice.game.Game
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.time.FTimeListener
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class FrameImageResource(val game: Game, val list: MutableList<ImageResource>, div: Int, val loop: Boolean) : ImageResource() {
	constructor(game: Game, list: MutableList<ImageResource>, div: Int) :
	this(game, list, div, true)

	constructor(game: Game, list: Array<ImageResource>, div: Int, loop: Boolean) :
	this(game, list.toMutableList(), div, loop)

	constructor(game: Game, list: Array<ImageResource>, div: Int) :
	this(game, list.toMutableList(), div, true)

	private var start: Long
	private val timer: FTimeListener
	private var counter = 0
	private var ended = false

	override var image: BufferedImage
		get() = if (loop || ended) list.last().image else list.getImage(counter).image
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
			FLog.e("fuck")
			counter++
			counter %= list.size
		})
	}
}