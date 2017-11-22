package org.frice.platform

import org.frice.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
interface FriceDrawer {
	var color: ColorResource
	fun init()
	fun drawOval(x: Double, y: Double, width: Double, height: Double)
	fun drawString(string: String, x: Double, y: Double)
	fun drawImage(image: FriceImage, x: Double, y: Double)
	fun drawRect(x: Double, y: Double, width: Double, height: Double)
	fun drawLine(x: Double, y: Double, width: Double, height: Double)
	fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double)
	fun stringSize(size: Double)
	fun newFont(name: String, size: Double)
	fun rotate(theta: Double)
	fun restore()
}

