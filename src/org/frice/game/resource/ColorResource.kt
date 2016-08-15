package org.frice.game.resource

import java.awt.Color

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorResource(val color: Color) : FResource {

	companion object {
		val GREEN = ColorResource(Color.GREEN)
		val BLUE = ColorResource(Color.BLUE)
		val GRAY = ColorResource(Color.GRAY)
		val DARK_GRAY = ColorResource(Color.DARK_GRAY)
		val LIGHT_GRAY = ColorResource(Color.LIGHT_GRAY)
		val WHITE = ColorResource(Color.WHITE)
		val RED = ColorResource(Color.RED)
		val BLACK = ColorResource(Color.BLACK)
		val CYAN = ColorResource(Color.CYAN)
		val MAGENTA = ColorResource(Color.MAGENTA)
		val YELLOW = ColorResource(Color.YELLOW)
		val SHIT_YELLOW = ColorResource(Color(0x633516)) /* Color of poop, just make it happy */
		val ORANGE = ColorResource(Color.ORANGE)
		val PINK = ColorResource(Color.PINK)
	}

	constructor(color: Int):this(Color(color))

	constructor(color: String):this(Color.getColor(color))

	override fun getResource() = color

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ColorResource) return false
		if (color.rgb == other.color.rgb && color.alpha == other.color.alpha) return true
		return false
	}

	override fun hashCode() = color.hashCode()

}