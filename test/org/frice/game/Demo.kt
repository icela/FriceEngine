package org.frice.game

import org.frice.event.OnFrameClickEvent
import org.frice.event.OnFrameMouseEvent
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class Demo : Game() {
	override fun onInit() {
		bounds = Rectangle(100, 100, 640, 480)
		title = "Demo of Frice"
	}

	override fun onPaint(g: Graphics2D) {

	}

	override fun onMouse(e: OnFrameMouseEvent) {
	}

	override fun onExit() {
	}

	override fun onClick(e: OnFrameClickEvent) {
	}

}