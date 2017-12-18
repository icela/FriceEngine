/**
 * @author ice1000
 * @since v1.5.0
 */
@file:JvmName("Initializer")

package org.frice

import javafx.application.Application
import org.frice.platform.*
import org.frice.resource.manager.FManager
import org.frice.utils.cast
import org.frice.utils.loop
import org.frice.utils.message.FLog
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
		when {
			osName.startsWith("Windows") -> onWindows?.invoke()
			osName.startsWith("Linux") -> onLinux?.invoke()
			osName.startsWith("Mac") -> onMac?.invoke()
			osName.startsWith("Solaris") || osName.startsWith("SunOS") -> onSolaris?.invoke()
			osName.startsWith("FreeBSD") -> onBSD?.invoke()
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

/**
 * Game launcher.
 * For Game/GameFX.
 *
 * @author ice1000
 * @since v1.5.0
 */
fun <GameType : FriceGame<*>> launch(c: Class<out GameType>) {
	if (GameFX::class.java.isAssignableFrom(c)) launchFx(cast(c))
	val instance: GameType = c.newInstance()
	if (instance is Game) launch(instance)
	else throw IllegalArgumentException("You should launch an instance of Game or GameFX!")
}
