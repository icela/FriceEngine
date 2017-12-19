package org.frice.resource.graphics

import org.frice.resource.FResource
import org.frice.utils.*
import java.awt.Color

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 * @see java.awt.SystemColor
 * @see java.awt.Color
 */
class ColorResource private constructor(
	val color: Int,
	private var `color tmp obj`: Color?) : FResource {

	internal fun `get reused color`() = `color tmp obj` ?: run {
		val rua = Color(color)
		`color tmp obj` = rua
		return@run rua
	}

	/**
	 * 颜表立。。。
	 * Read these color names in UTF-8 w/o BOM encoding, and pick up your 信仰 and 节操.
	 */
	@Suppress("ObjectPropertyName", "unused")
	companion object {
		@JvmField val GREEN = ColorResource(Color.GREEN)
		@JvmField val BLUE = ColorResource(Color.BLUE)
		@JvmField val GRAY = ColorResource(Color.GRAY)
		@JvmField val DARK_GRAY = ColorResource(Color.DARK_GRAY)
		@JvmField val LIGHT_GRAY = ColorResource(Color.LIGHT_GRAY)
		@JvmField val WHITE = ColorResource(Color.WHITE)
		@JvmField val RED = ColorResource(Color.RED)
		@JvmField val BLACK = ColorResource(Color.BLACK)
		@JvmField val CYAN = ColorResource(Color.CYAN)
		@JvmField val MAGENTA = ColorResource(Color.MAGENTA)
		@JvmField val YELLOW = ColorResource(Color.YELLOW)
		@JvmField val SHIT_YELLOW = ColorResource(0x633516)
		@JvmField val ORANGE = ColorResource(Color.ORANGE)
		@JvmField val PINK = ColorResource(Color.PINK)
		@JvmField val COLORLESS = ColorResource(Color(0, 0, 0, 0))
		@JvmField val PURPLE = ColorResource(0x781895)
		@JvmField val 小埋色 = ColorResource(0xFFAC2B)
		@JvmField val 基佬紫 = PURPLE
		@JvmField val 八云紫 = 基佬紫
		@JvmField val 八云蓝 = BLUE
		@JvmField val 吾王蓝 = 八云蓝
		@JvmField val 教主黄 = YELLOW
		@JvmField val 宝强绿 = GREEN
		@JvmField val 如果奇迹有颜色那么一定是橙色 = ORANGE
		@JvmField val 高坂穗乃果 = 如果奇迹有颜色那么一定是橙色
		@JvmField val 南小鸟 = GRAY
		@JvmField val 园田海未 = BLUE
		@JvmField val 洵濑绘理 = ColorResource(0x0FFFFF)
		@JvmField val 星空凛 = 教主黄
		@JvmField val 西木野真姬 = RED
		@JvmField val 西木野取款姬 = 西木野真姬
		@JvmField val 东条希 = 八云紫
		@JvmField val 小泉花阳 = ColorResource(0x1BA61C)
		@JvmField val 矢泽妮可 = PINK
		@JvmField val 屎黄色 = SHIT_YELLOW
		@JvmField val 天依蓝 = ColorResource(0x66CCFF)
		@JvmField val 清真绿 = ColorResource(0x038B43)
		@JvmField val IntelliJ_IDEA黑 = ColorResource(0x2B2B2B)
		@JvmField val 如果真爱有颜色那么一定是黄色 = 教主黄
		@JvmField val 杀老师 = 如果真爱有颜色那么一定是黄色
		@JvmField val 潮田渚 = 园田海未
		@JvmField val 茅野枫 = 宝强绿
		@JvmField val 赤羽业 = 西木野取款姬
	}

	constructor(color: Int) : this(color, null)
	constructor(color: Color) : this(makeColor(color), color)
	constructor(color: String) : this(Color.getColor(color))
	constructor(rgb: Int, alpha: Int) : this(makeColor(rgb, alpha))
	constructor(red: Int, green: Int, blue: Int, alpha: Int) : this(makeColor(red, green, blue, alpha))

	/** @see java.awt.Color */
	fun darker() = ColorResource(
		Math.max((red * 0.7).toInt(), 0),
		Math.max((green * 0.7).toInt(), 0),
		Math.max((blue * 0.7).toInt(), 0),
		alpha)

	/** @see java.awt.Color */
	fun brighter(): ColorResource {
		var r = red
		var g = green
		var b = blue
		val mid = 3
		return if (r == 0 && g == 0 && b == 0) {
			ColorResource(mid, mid, mid, alpha)
		} else {
			if (r in 1..(mid - 1)) r = mid
			if (g in 1..(mid - 1)) g = mid
			if (b in 1..(mid - 1)) b = mid
			ColorResource(Math.min((r / 0.7).toInt(), 255),
				Math.min((g / 0.7).toInt(), 255),
				Math.min((b / 0.7).toInt(), 255),
				alpha)
		}
	}

	fun greyify() = ColorResource(color.greyify())
	fun bluify() = ColorResource(color.bluify())
	fun redify() = ColorResource(color.redify())
	fun greenify() = ColorResource(color.greenify())
	val red get() = color.red
	val blue get() = color.blue
	val green get() = color.green
	val alpha get() = color.alpha
	val average get() = color.average

	/**
	 * not for users and developers.
	 * this should only be called by the engine itself.
	 */
	override val resource get() = color

	override fun hashCode() = color
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ColorResource) return false
		return color == other.color
	}
}