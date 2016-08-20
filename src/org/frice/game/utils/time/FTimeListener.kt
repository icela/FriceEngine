package org.frice.game.utils.time

/**
 * @param times if the value is -1, will loop.
 *
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 */
class FTimeListener(time: Int, times: Int, val timeUp: () -> Unit) : FTimer(time, times) {
	constructor(time: Int, times: Int, timeUp: OnTimeEvent) : this(time, times, { timeUp.execute() })
	constructor(time: Int, timeUp: OnTimeEvent) : this(time, -1, { timeUp.execute() })
	constructor(time: Int, timeUp: () -> Unit) : this(time, -1, timeUp)

	fun check() = if (ended() && times != 0) {
		if (times > 0) times--
		timeUp.invoke()
	} else Unit
}