/**
 * @author ice1000
 * @since v1.5.0
 */
@file:JvmMultifileClass
@file:JvmName("Initializer")

package org.frice

import javafx.application.Application
import org.frice.platform.FriceGame
import org.frice.utils.loop
import org.frice.utils.message.FLog
import java.awt.Rectangle
import java.util.*
import javax.swing.UIManager
import javax.swing.WindowConstants
import kotlin.concurrent.thread

@Suppress("ClassName")
object `{-# LANGUAGE Initializer #-}` {
	init {
		val osName = System.getProperty("os.name")
		FLog.v("Operating system: $osName")
		if ("Windows" in osName)
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
		else if ("Linux" in osName) UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
	}
}

val TO_X = 100
val TO_Y = 100

val SMALL_PHONE = Rectangle(TO_X, TO_Y, 480, 800)
val BIG_PHONE = Rectangle(TO_X, TO_Y, 720, 1200)
val HUGE_PHONE = Rectangle(TO_X, TO_Y, 1080, 1920)

val SMALL_SQUARE = Rectangle(TO_X, TO_Y, 400, 400)
val BIG_SQUARE = Rectangle(TO_X, TO_Y, 800, 800)

fun Rectangle.rotate() {
	width -= -height
	height -= width
	width += height
}

fun launch(game: GameFX) = launchFx(game)

fun launchFx(game: Class<out GameFX>) = launchFx(game.newInstance())

fun launchFx(game: GameFX) {
	`{-# LANGUAGE Initializer #-}`
	FLog.v("Engine start!")
	Application.launch(game.javaClass)
}

fun launch(game: Game) {
	`{-# LANGUAGE Initializer #-}`
	game.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
	FLog.v("Engine start!")
	game.run {
		onLastInit()
		thread {
			loop {
				try {
					onRefresh()
					// only update per "refreshTime"
					if (!paused && !stopped && refresh.ended()) {
						panel.repaint()
						fpsCounter.refresh()
					}
				} catch (ignored: ConcurrentModificationException) {
				}
			}
		}
	}
	FLog.v("Engine thread successfully created.")
}

fun launch(c: Class<out FriceGame>) = c.newInstance().let { instance ->
	`{-# LANGUAGE Initializer #-}`
	when (instance) {
		is Game -> launch(instance)
		is GameFX -> launch(instance)
		else -> throw IllegalArgumentException("You should launch an instance of Game or GameFX!")
	}
}
