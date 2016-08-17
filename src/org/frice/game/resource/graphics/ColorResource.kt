package org.frice.game.resource.graphics

import org.frice.game.resource.FResource
import java.awt.Color

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorResource(val color: Color) : FResource {

	companion object {
		@JvmStatic val GREEN = ColorResource(Color.GREEN)
		@JvmStatic val BLUE = ColorResource(Color.BLUE)
		@JvmStatic val GRAY = ColorResource(Color.GRAY)
		@JvmStatic val DARK_GRAY = ColorResource(Color.DARK_GRAY)
		@JvmStatic val LIGHT_GRAY = ColorResource(Color.LIGHT_GRAY)
		@JvmStatic val WHITE = ColorResource(Color.WHITE)
		@JvmStatic val RED = ColorResource(Color.RED)
		@JvmStatic val BLACK = ColorResource(Color.BLACK)
		@JvmStatic val CYAN = ColorResource(Color.CYAN)
		@JvmStatic val MAGENTA = ColorResource(Color.MAGENTA)
		@JvmStatic val YELLOW = ColorResource(Color.YELLOW)
		@JvmStatic val SHIT_YELLOW = ColorResource(Color(0x633516))
		@JvmStatic val ORANGE = ColorResource(Color.ORANGE)
		@JvmStatic val PINK = ColorResource(Color.PINK)
		@JvmStatic val 小埋色 = ColorResource(Color(0xFFAC2B))
		@JvmStatic val 基佬紫 = ColorResource(Color(0x781895))
		@JvmStatic val 吾王蓝 = BLUE
		@JvmStatic val 教主黄 = YELLOW
		@JvmStatic val 宝强绿 = GREEN
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