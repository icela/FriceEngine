package org.frice

import org.frice.Initializer.launch
import org.frice.utils.time.FTimeListener

class TimerTest : org.frice.Game() {
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