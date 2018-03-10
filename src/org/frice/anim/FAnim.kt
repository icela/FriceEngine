package org.frice.anim

import org.frice.obj.FObject
import org.frice.util.time.FClock

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class FAnim {
	protected val start = FClock.current.toDouble()
	protected val now: Double get() = FClock.current.toDouble()
	protected var lastRefresh: Double = start

	@Suppress("FunctionName")
	internal abstract fun `{-# do #-}`(obj: FObject)
}
