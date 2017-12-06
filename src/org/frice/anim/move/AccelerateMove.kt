package org.frice.anim.move

import org.frice.anim.move.DoublePair.Factory.from1000
import org.frice.obj.AbstractObject

/**
 * Move with force (accelerate), give accelerate value to ax and by
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 * @param ax accelerate on x
 * @param ay accelerate on y
 */
class AccelerateMove(var ax: Double, var ay: Double) : SimpleMove(0, 0) {
	private var mx = 0.0
	private var my = 0.0

	override val delta: DoublePair
		get() {
			mx = (now - start) * ax / 2.0
			my = (now - start) * ay / 2.0
			return from1000((now - lastRefresh) / 1000 * mx, (now - lastRefresh) / 1000 * my)
		}
}

//class ChasingMove(self: AbstractObject, var targetObj: AbstractObject, var speed: Double) : SelfCenteredMoveAnim(self) {
//	override val delta: DoublePair
//		get() {
//		}
//}
