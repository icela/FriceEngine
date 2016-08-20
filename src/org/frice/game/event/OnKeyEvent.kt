package org.frice.game.event

import java.awt.event.KeyEvent

/**
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since v0.3.2
 */
interface OnKeyEvent {
	fun execute(e: KeyEvent)
}