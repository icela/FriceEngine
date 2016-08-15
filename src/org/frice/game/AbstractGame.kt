package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.resource.ColorResource
import org.frice.game.resource.FResource
import java.awt.Frame

/**
 * First game class(not for you)
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
abstract class AbstractGame() : Frame(), Runnable {
	protected var paused = false
	protected var back: FResource = ColorResource.SHIT_YELLOW
	protected var refreshPerSecond = 15
	protected var debug = true

	protected abstract fun onInit()
	protected abstract fun onRefresh()
	protected abstract fun onClick(e: OnClickEvent?)
	protected abstract fun onMouse(e: OnMouseEvent?)
	protected open fun onExit() = System.exit(0)
	protected open fun onLoseFocus(e: OnWindowEvent?) {
		paused = true
	}

	protected open fun onFocus(e: OnWindowEvent?) {
		paused = false
	}

}