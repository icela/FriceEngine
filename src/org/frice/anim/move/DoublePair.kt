package org.frice.anim.move

data class DoublePair(var x: Double, var y: Double) {

	companion object Factory {
		@JvmStatic
		fun from1000(x: Double, y: Double) = org.frice.anim.move.DoublePair(x / 1000.0, y / 1000.0)
	}

	operator fun plusAssign(d: org.frice.anim.move.DoublePair) {
		x += d.x
		y += d.y
	}

	operator fun plusAssign(d: Double) {
		x += d
		y += d
	}

	operator fun minusAssign(d: org.frice.anim.move.DoublePair) {
		x -= d.x
		y -= d.y
	}

	operator fun minusAssign(d: Double) {
		x -= d
		y -= d
	}

	operator fun timesAssign(d: Double) {
		x *= d
		y *= d
	}

	operator fun divAssign(d: Double) {
		x /= d
		y /= d
	}

	operator fun div(d: Double) = org.frice.anim.move.DoublePair(x / d, y / d)
	operator fun times(d: Double) = org.frice.anim.move.DoublePair(x * d, y * d)
	operator fun plus(d: Double) = org.frice.anim.move.DoublePair(x + d, y + d)
	operator fun plus(d: org.frice.anim.move.DoublePair) = org.frice.anim.move.DoublePair(x + d.x, y + d.y)
	operator fun minus(d: Double) = org.frice.anim.move.DoublePair(x - d, y - d)
	operator fun minus(d: org.frice.anim.move.DoublePair) = org.frice.anim.move.DoublePair(x - d.x, y - d.y)
	operator fun inc() = org.frice.anim.move.DoublePair(x++, y++)
	operator fun dec() = org.frice.anim.move.DoublePair(x--, y--)
	operator fun unaryPlus() = org.frice.anim.move.DoublePair(x, y)
	operator fun unaryMinus() = org.frice.anim.move.DoublePair(-x, -y)
}