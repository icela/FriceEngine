package org.frice.game.utils.graphics.shape

import java.util.HashSet

/**
 * 通过两点构造一条直线
 * copied from https://github.com/ice1000/FindLine/blob/master/src/ice1000/models/Line.kt
 * my another repo
 * Created by ice1000 on 2016/8/8.
 *
 * @author ice1000
 */
open class FLine(one: FPoint, two: FPoint) {

	private val a = two.y - one.y
	private val b = one.x - two.x
	private val c = two.x * one.y - one.x * two.y
	val set = HashSet<FPoint>()

	init {
		if (a != 0 || b != 0) {
			(Math.min(one.x, two.x)..Math.max(one.x, two.x))
					.forEach { x -> set.add(FPoint(x, x2y(x))) }
			(Math.min(one.y, two.y)..Math.max(one.y, two.y))
					.forEach { y -> set.add(FPoint(y2x(y), y)) }
		}
	}

	fun x2y(x: Int) = if (b == 0) c / a else -(a * x + c) / b
	fun y2x(y: Int) = if (a == 0) c / b else -(b * y + c) / a

	override operator fun equals(other: Any?): Boolean {
		if (other == null || other !is FLine) return false
		return a / other.a == b / other.b && b / other.b == c / other.c
	}
}