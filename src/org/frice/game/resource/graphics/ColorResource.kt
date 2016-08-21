package org.frice.game.resource.graphics

import org.frice.game.resource.FResource
import java.awt.Color

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorResource(val color: Color) : FResource {

	/**
	 * 颜表立。。。
	 */
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
		@JvmStatic val SHIT_YELLOW = ColorResource(0x633516)
		@JvmStatic val ORANGE = ColorResource(Color.ORANGE)
		@JvmStatic val PINK = ColorResource(Color.PINK)
		@JvmStatic val 小埋色 = ColorResource(0xFFAC2B)
		@JvmStatic val 基佬紫 = ColorResource(0x781895)
		@JvmStatic val 吾王蓝 = BLUE
		@JvmStatic val 教主黄 = YELLOW
		@JvmStatic val 宝强绿 = GREEN
		@JvmStatic val 冰封绿 = 宝强绿
		@JvmStatic val 如果奇迹有颜色那么一定是橙色 = ORANGE
		@JvmStatic val 高坂穗乃果 = ORANGE
		@JvmStatic val 南小鸟 = GRAY
		@JvmStatic val 园田海未 = BLUE
		@JvmStatic val 洵濑绘理 = ColorResource(0x0FFFFF)
		@JvmStatic val 星空凛 = 教主黄
		@JvmStatic val 西木野真姬 = RED
		@JvmStatic val 东条希 = 基佬紫
		@JvmStatic val 小泉花阳 = ColorResource(0x1BA61C)
		@JvmStatic val 矢泽妮可 = PINK
		@JvmStatic val 屎黄色 = SHIT_YELLOW
		@JvmStatic val 天依蓝 = ColorResource(0x66CCFF)
		@JvmStatic val 清真绿 = ColorResource(0x038B43)
	}

	constructor(color: Int):this(Color(color))

	constructor(color: String):this(Color.getColor(color))

	/**
	 * not for users and developer.
	 * this should only be called in the engine core.
	 */
	override fun getResource() = color

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ColorResource) return false
		if (color.rgb == other.color.rgb && color.alpha == other.color.alpha) return true
		return false
	}

	override fun hashCode() = color.hashCode()

}