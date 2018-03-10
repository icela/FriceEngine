package org.frice.obj

import org.frice.anim.FAnim
import org.frice.resource.FResource
import java.util.*

/**
 * The base class of all concrete game objects
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class FObject(x: Double, y: Double) : PhysicalObject(x, y) {
	var id = -1
	val anims = LinkedList<FAnim>()
		@Deprecated("Replace with `addAnim` or `clearAnim`", level = DeprecationLevel.WARNING) get
	abstract val resource: FResource

	abstract fun scale(x: Double, y: Double)

	@Suppress("FunctionName", "DEPRECATION")
	internal fun `{-# runAnims #-}`() = anims.forEach { it.`{-# do #-}`(this) }

	@Suppress("DEPRECATION")
	fun addAnim(anim: FAnim) = anims.add(anim)

	@Suppress("DEPRECATION")
	fun stopAnims() = anims.clear()
}
