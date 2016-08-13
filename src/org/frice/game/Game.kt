package org.frice.game

import java.awt.Frame

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v1.0
 */
abstract class Game(): Frame() {
	init {
		isVisible = true
		init()
	}
	abstract fun init()
	abstract fun load()
	abstract fun 
}