package org.frice.anim.move

data class DoublePair(var x: Double, var y: Double) {

	companion object Factory {
		fun fromK(x: Double, y: Double) = DoublePair(x / 1e3, y / 1e3)
		fun fromM(x: Double, y: Double) = DoublePair(x / 1e6, y / 1e6)
	}

}