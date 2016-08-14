package org.frice.utils.time

import org.frice.game.event.OnTimeEvent

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 */
class FTimeListener(time: Int, val timeUp: () -> Unit) : FTimer(time) {
	constructor(time: Int, timeUp: OnTimeEvent) : this(time, { timeUp.execute() })

	fun check() = if (ended()) timeUp.invoke() else Unit
}