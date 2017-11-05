package org.frice.resource.graphics

import org.frice.resource.FResource
import java.awt.Color

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorResource(val color: Color) : FResource {

	/**
	 * 颜表立。。。
	 * Read these color names in UTF-8 w/o BOM encoding, and pick up your 信仰 and 节操.
	 */
	companion object {
		@JvmField
		val GREEN = ColorResource(Color.GREEN)
		@JvmField
		val BLUE = ColorResource(Color.BLUE)
		@JvmField
		val GRAY = ColorResource(Color.GRAY)
		@JvmField
		val DARK_GRAY = ColorResource(Color.DARK_GRAY)
		@JvmField
		val LIGHT_GRAY = ColorResource(Color.LIGHT_GRAY)
		@JvmField
		val WHITE = ColorResource(Color.WHITE)
		@JvmField
		val RED = ColorResource(Color.RED)
		@JvmField
		val BLACK = ColorResource(Color.BLACK)
		@JvmField
		val CYAN = ColorResource(Color.CYAN)
		@JvmField
		val MAGENTA = ColorResource(Color.MAGENTA)
		@JvmField
		val YELLOW = ColorResource(Color.YELLOW)
		@JvmField
		val SHIT_YELLOW = ColorResource(0x633516)
		@JvmField
		val ORANGE = ColorResource(Color.ORANGE)
		@JvmField
		val PINK = ColorResource(Color.PINK)
		@JvmField
		val COLORLESS = ColorResource(Color(0, 0, 0, 0))
		@JvmField
		val 小埋色 = ColorResource(0xFFAC2B)
		@JvmField
		val 基佬紫 = ColorResource(0x781895)
		@JvmField
		val 吾王蓝 = BLUE
		@JvmField
		val 教主黄 = YELLOW
		@JvmField
		val 宝强绿 = GREEN
		@JvmField
		val 冰封绿 = 宝强绿
		@JvmField
		val 如果奇迹有颜色那么一定是橙色 = ORANGE
		@JvmField
		val 高坂穗乃果 = ORANGE
		@JvmField
		val 南小鸟 = GRAY
		@JvmField
		val 园田海未 = BLUE
		@JvmField
		val 洵濑绘理 = ColorResource(0x0FFFFF)
		@JvmField
		val 星空凛 = 教主黄
		@JvmField
		val 西木野真姬 = RED
		@JvmField
		val 东条希 = 基佬紫
		@JvmField
		val 小泉花阳 = ColorResource(0x1BA61C)
		@JvmField
		val 矢泽妮可 = PINK
		@JvmField
		val 屎黄色 = SHIT_YELLOW
		@JvmField
		val 天依蓝 = ColorResource(0x66CCFF)
		@JvmField
		val 清真绿 = ColorResource(0x038B43)
		@JvmField
		val IntelliJ_IDEA黑 = ColorResource(0x2B2B2B)
		@JvmField
		val 如果真爱有颜色那么一定是黄色 = 教主黄
		@JvmField
		val 杀老师 = 如果真爱有颜色那么一定是黄色
		@JvmField
		val 潮田渚 = 园田海未
		@JvmField
		val 茅野枫 = 冰封绿
		@JvmField
		val 赤羽业 = 西木野真姬
	}

	constructor(color: Int) : this(Color(color))

	constructor(color: String) : this(Color.getColor(color))

	/**
	 * not for users and developers.
	 * this should only be called by the engine itself.
	 */
	override val resource get() = color

	fun darker() = ColorResource(color.darker())

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ColorResource) return false
		if (color.rgb == other.color.rgb && color.alpha == other.color.alpha) return true
		return false
	}

	override fun hashCode() = color.hashCode()
}