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

/**
 * Chase after the target, same speed, different direction
 * @author ice1000
 * @since v1.7.3
 * @param self the chasing object
 * @param targetObj the chased target
 */
class ChasingMove(self: AbstractObject, var targetObj: AbstractObject, var speed: Double) : SelfCenteredMoveAnim(self) {
	override val delta: DoublePair
		get() {
			val a = targetObj.x - self.x
			val b = targetObj.y - self.y
			val c = Math.sqrt(a * a + b * b)
			val deltaTime = now - lastRefresh
			val pair = DoublePair.from1000(deltaTime * (speed * a / c), deltaTime * (speed * b / c))
			lastRefresh = now
			return pair
		}
}

/**
 * Approaching the target, each second
 * @author ice1000
 * @since v1.7.3
 * @param self the approaching object
 * @param targetObj the approached target
 */
class ApproachingMove(self: AbstractObject, var targetObj: AbstractObject, var proportion: Double) : SelfCenteredMoveAnim(self) {
	override val delta: DoublePair
		get() {
			val a = targetObj.x - self.x
			val b = targetObj.y - self.y
			@Suppress("LocalVariableName")
			val `deltaTime*proportion` = (now - lastRefresh) * proportion
			val pair = DoublePair.from1000(`deltaTime*proportion` * (a), `deltaTime*proportion` * (b))
			lastRefresh = now
			return pair
		}
}

