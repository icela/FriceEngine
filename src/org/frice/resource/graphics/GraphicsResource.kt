package org.frice.resource.graphics

import org.frice.platform.FriceImage
import org.frice.platform.adapter.JvmImage
import org.frice.resource.FResource
import org.frice.utils.misc.forceRun
import java.awt.Color

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorResource(val color: Color) : org.frice.resource.FResource {

	/**
	 * 颜表立。。。
	 * Read these color names in UTF-8 w/o BOM encoding, and pick up your 信仰 and 节操.
	 */
	companion object {
		@JvmField val GREEN = org.frice.resource.graphics.ColorResource(Color.GREEN)
		@JvmField val BLUE = org.frice.resource.graphics.ColorResource(Color.BLUE)
		@JvmField val GRAY = org.frice.resource.graphics.ColorResource(Color.GRAY)
		@JvmField val DARK_GRAY = org.frice.resource.graphics.ColorResource(Color.DARK_GRAY)
		@JvmField val LIGHT_GRAY = org.frice.resource.graphics.ColorResource(Color.LIGHT_GRAY)
		@JvmField val WHITE = org.frice.resource.graphics.ColorResource(Color.WHITE)
		@JvmField val RED = org.frice.resource.graphics.ColorResource(Color.RED)
		@JvmField val BLACK = org.frice.resource.graphics.ColorResource(Color.BLACK)
		@JvmField val CYAN = org.frice.resource.graphics.ColorResource(Color.CYAN)
		@JvmField val MAGENTA = org.frice.resource.graphics.ColorResource(Color.MAGENTA)
		@JvmField val YELLOW = org.frice.resource.graphics.ColorResource(Color.YELLOW)
		@JvmField val SHIT_YELLOW = org.frice.resource.graphics.ColorResource(0x633516)
		@JvmField val ORANGE = org.frice.resource.graphics.ColorResource(Color.ORANGE)
		@JvmField val PINK = org.frice.resource.graphics.ColorResource(Color.PINK)
		@JvmField val COLORLESS = org.frice.resource.graphics.ColorResource(Color(0, 0, 0, 0))
		@JvmField val 小埋色 = org.frice.resource.graphics.ColorResource(0xFFAC2B)
		@JvmField val 基佬紫 = org.frice.resource.graphics.ColorResource(0x781895)
		@JvmField val 吾王蓝 = org.frice.resource.graphics.ColorResource.Companion.BLUE
		@JvmField val 教主黄 = org.frice.resource.graphics.ColorResource.Companion.YELLOW
		@JvmField val 宝强绿 = org.frice.resource.graphics.ColorResource.Companion.GREEN
		@JvmField val 冰封绿 = org.frice.resource.graphics.ColorResource.Companion.宝强绿
		@JvmField val 如果奇迹有颜色那么一定是橙色 = org.frice.resource.graphics.ColorResource.Companion.ORANGE
		@JvmField val 高坂穗乃果 = org.frice.resource.graphics.ColorResource.Companion.ORANGE
		@JvmField val 南小鸟 = org.frice.resource.graphics.ColorResource.Companion.GRAY
		@JvmField val 园田海未 = org.frice.resource.graphics.ColorResource.Companion.BLUE
		@JvmField val 洵濑绘理 = org.frice.resource.graphics.ColorResource(0x0FFFFF)
		@JvmField val 星空凛 = org.frice.resource.graphics.ColorResource.Companion.教主黄
		@JvmField val 西木野真姬 = org.frice.resource.graphics.ColorResource.Companion.RED
		@JvmField val 东条希 = org.frice.resource.graphics.ColorResource.Companion.基佬紫
		@JvmField val 小泉花阳 = org.frice.resource.graphics.ColorResource(0x1BA61C)
		@JvmField val 矢泽妮可 = org.frice.resource.graphics.ColorResource.Companion.PINK
		@JvmField val 屎黄色 = org.frice.resource.graphics.ColorResource.Companion.SHIT_YELLOW
		@JvmField val 天依蓝 = org.frice.resource.graphics.ColorResource(0x66CCFF)
		@JvmField val 清真绿 = org.frice.resource.graphics.ColorResource(0x038B43)
		@JvmField val IntelliJ_IDEA黑 = org.frice.resource.graphics.ColorResource(0x2B2B2B)
		@JvmField val 如果真爱有颜色那么一定是黄色 = org.frice.resource.graphics.ColorResource.Companion.教主黄
		@JvmField val 杀老师 = org.frice.resource.graphics.ColorResource.Companion.如果真爱有颜色那么一定是黄色
		@JvmField val 潮田渚 = org.frice.resource.graphics.ColorResource.Companion.园田海未
		@JvmField val 茅野枫 = org.frice.resource.graphics.ColorResource.Companion.冰封绿
		@JvmField val 赤羽业 = org.frice.resource.graphics.ColorResource.Companion.西木野真姬
	}

	constructor(color: Int) : this(Color(color))

	constructor(color: String) : this(Color.getColor(color))

	/**
	 * not for users and developers.
	 * this should only be called by the engine itself.
	 */
	override fun getResource() = color

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is org.frice.resource.graphics.ColorResource) return false
		if (color.rgb == other.color.rgb && color.alpha == other.color.alpha) return true
		return false
	}

	override fun hashCode() = color.hashCode()
}


/**
 * Created by ice1000 on 2016/8/27.
 *
 * @author ice1000
 * @since v0.4
 */
class FunctionResource(color: org.frice.resource.graphics.ColorResource, val f: (Double) -> Double, width: Int, height: Int) : org.frice.resource.FResource {

	private val image: org.frice.platform.FriceImage

	init {
		image = org.frice.platform.adapter.JvmImage(width, height)
		var lastTime = f(0.0)
		var thisTime: Double
		for (x in 0..width step 1) {
			thisTime = f(x.toDouble())
			forceRun { image[x, thisTime.toInt()] = color }
			if (Math.abs(thisTime - lastTime) >= 1.0) forceRun {
				for (i in Math.min(thisTime, lastTime).toInt()..Math.max(thisTime, lastTime).toInt()) image[x, i] = color
			}
			lastTime = thisTime
		}
	}

	override fun getResource() = image
}

/**
 * used to represent a Curve.
 * something like circle.
 */
class CurveResource(color: org.frice.resource.graphics.ColorResource, val f: (Double) -> List<Double>, width: Int, height: Int) : org.frice.resource.FResource {
	private val image: org.frice.platform.FriceImage

	init {
		image = org.frice.platform.adapter.JvmImage(width, height)
		for (x in 0..width step 1) {
			for (y in f(x.toDouble())) {
				forceRun { image[x, y.toInt()] = color }
			}
		}
	}

	override fun getResource() = image
}

