package org.frice.util.time

class FpsCounter {
	var counter = 0
	var display = 0
	var timer = FTimer(1000)

	fun refresh() {
		++counter
		if (timer.ended()) {
			display = counter
			counter = 0
		}
	}
}
