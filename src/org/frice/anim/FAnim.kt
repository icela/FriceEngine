package org.frice.anim

import org.frice.utils.time.Clock

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class FAnim {
	protected val start = Clock.current.toDouble()

	protected val now: Double get() = Clock.current.toDouble()
}