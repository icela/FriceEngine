/**
 * @author ice1000
 * @since v1.5.0
 */
@file:JvmName("Initializer")

package org.frice

import javafx.application.Application
import org.frice.platform.*
import org.frice.resource.manager.FManager
import org.frice.util.*
import org.frice.util.message.FLog
import java.awt.Rectangle
import java.util.*
import javax.swing.UIManager
import javax.swing.WindowConstants
import kotlin.concurrent.thread

/**
 * You're not expected to use this class yourself.
 * @author ice1000
 * @since v1.5.0
 */
@Suppress("ClassName")
object `{-# LANGUAGE Initializer #-}` {
	init {
		val osName = System.getProperty("os.name")
		FLog.v("Operating system: $osName")
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
		isOnWindows = osName.startsWith("Windows")
		isOnLinux = osName.startsWith("Linux")
		isOnMac = osName.startsWith("Mac")
		isOnSolaris = osName.startsWith("Solaris") || osName.startsWith("SunOS")
		isOnBSD = osName.startsWith("FreeBSD")
		when {
			isOnWindows -> onWindows?.run()
			isOnLinux -> onLinux?.run()
			isOnMac -> onMac?.run()
			isOnSolaris -> onSolaris?.run()
			isOnBSD -> onBSD?.run()
		}
	}
}

const val TO_X = 100
const val TO_Y = 100

@JvmField val SMALL_PHONE = Rectangle(TO_X, TO_Y, 480, 800)
@JvmField val BIG_PHONE = Rectangle(TO_X, TO_Y, 720, 1200)
@JvmField val HUGE_PHONE = Rectangle(TO_X, TO_Y, 1080, 1920)

@JvmField val SMALL_SQUARE = Rectangle(TO_X, TO_Y, 400, 400)
@JvmField val BIG_SQUARE = Rectangle(TO_X, TO_Y, 800, 800)

var sleepOnRefresh = true

fun Rectangle.rotate() {
	width -= -height
	height -= width
	width += height
}

/**
 * GameFX launcher.
 * This is actually redundant, just to avoid naming conflict
 *
 * @author ice1000
 * @since v1.5.0
 */
fun launchFx(game: Class<out GameFX>) {
	`{-# LANGUAGE Initializer #-}`
	FManager.useJfx = true
	FLog.v("Engine start!")
	Application.launch(game)
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
					adjust()
					// only update per "refreshTime"
					if (!paused && !stopped && refresh.ended()) {
						panel.repaint()
						fpsCounter.refresh()
						val deltaTime = refresh.time / 2
						if (sleepOnRefresh) pause(deltaTime.toLong())
					}
				} catch (ignored: ConcurrentModificationException) {
				} catch (ignored: InterruptedException) {
				}
			}
		}
	}
	FLog.v("Engine thread successfully created.")
}

/**
 * Game launcher.
 * For Game/GameFX.
 *
 * @author ice1000
 * @since v1.5.0
 */
fun <GameType : FriceGame> launch(c: Class<out GameType>) {
	if (GameFX::class.java.isAssignableFrom(c)) launchFx(cast(c))
	val instance: GameType = c.newInstance()
	if (instance is Game) launch(instance)
	else throw IllegalArgumentException("You should launch an instance of Game or GameFX!")
}
