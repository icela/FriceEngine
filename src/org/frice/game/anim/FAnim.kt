package org.frice.game.anim

import org.frice.game.utils.time.Clock

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class FAnim() {
	protected val start: Double = Clock.current.toDouble()

	protected val now: Double
		get() = Clock.current.toDouble()
}