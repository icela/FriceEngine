package org.frice.anim.move

data class DoublePair(var x: Double, var y: Double) {

	companion object Factory {
		fun fromK(x: Double, y: Double) = DoublePair(x / 1e3, y / 1e3)
		fun fromM(x: Double, y: Double) = DoublePair(x / 1e6, y / 1e6)
	}

	operator fun plusAssign(d: DoublePair) {
		x += d.x
		y += d.y
	}

	operator fun plusAssign(d: Double) {
		x += d
		y += d
	}

	operator fun minusAssign(d: DoublePair) {
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

	operator fun div(d: Double) = DoublePair(x / d, y / d)
	operator fun times(d: Double) = DoublePair(x * d, y * d)
	operator fun plus(d: Double) = DoublePair(x + d, y + d)
	operator fun plus(d: DoublePair) = DoublePair(x + d.x, y + d.y)
	operator fun minus(d: Double) = DoublePair(x - d, y - d)
	operator fun minus(d: DoublePair) = DoublePair(x - d.x, y - d.y)
	operator fun inc() = DoublePair(x++, y++)
	operator fun dec() = DoublePair(x--, y--)
	operator fun unaryPlus() = DoublePair(x, y)
	operator fun unaryMinus() = DoublePair(-x, -y)
}