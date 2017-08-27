package org.frice.game

import org.frice.game.utils.time.FTimeListener

class TimerTest : Game() {
	override fun onInit() {
		super.onInit()
		addTimeListener(FTimeListener(1000, times = 1) {
			println("executed")
		})
	}
	companion object {
		@JvmStatic
		fun main(args: Array<String>) = launch(TimerTest::class.java)
	}
}