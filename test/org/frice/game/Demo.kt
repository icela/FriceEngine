package org.frice.game

import org.frice.game.event.OnFrameClickEvent
import org.frice.game.event.OnFrameMouseEvent
import org.frice.game.spirit.ImageObject
import org.frice.game.texture.FileTexture
import org.frice.utils.time.Timer
import java.awt.Rectangle
import java.io.File
import java.util.*

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class Demo : Game() {

	val dickTimer = Timer(1000)
	var fuck = 0
	val objList = ArrayList<ImageObject>()

	override fun onInit() {
		bounds = Rectangle(100, 100, 640, 480)
		title = "Demo of Frice"
	}

	override fun onMouse(e: OnFrameMouseEvent) {
	}

	override fun onRefresh() {
		if (dickTimer.ended()) {
			val texture = FileTexture("tres" + File.separator + "display.png")
			val obj: ImageObject
			if (fuck < 350) {
				obj = ImageObject(texture, fuck, fuck)
				objList.add(obj)
				addObject(obj)
				fuck += 100
			} else {
				obj = objList[objList.size - 1]
				objList.remove(obj)
				removeObject(obj)
				fuck -= 100
			}
		}
	}

	override fun onExit() {
	}

	override fun onClick(e: OnFrameClickEvent) {
	}

}