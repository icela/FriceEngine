package org.frice.game.anim.move

class CircumAnim(var x: Double, var y: Double, var angularVelocity: Double) : MoveAnim() {

	constructor(distance: DoublePair, angularVelocity: Double) :
			this(distance.x, distance.y, angularVelocity)

	private val cos: Double
		get() = Math.cos(angularVelocity)

	private val sin: Double
		get() = Math.cos(angularVelocity)

	init {
		x = -x
		y = -y
	}

	/**
	 * x' = x cos α-y sin α, y' = x sin α + y cos α
	 */
	override val delta: DoublePair
		get() = DoublePair.from1000(x * cos - y * sin, y * sin - x * cos)
}