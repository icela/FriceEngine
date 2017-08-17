package org.frice.game

import org.frice.game.utils.misc.BoolArray
import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BoolArrayTest {
	@Test
	fun test1() {
		val a = BoolArray(100)
		for (i in 0..99) assertFalse(a[i])
		a[0] = true
		assertTrue(a[0])
		a[1] = true
		assertTrue(a[1])
		a[10] = true
		assertTrue(a[10])
		a[50] = true
		assertTrue(a[50])
		(2..99)
				.filter { it !in listOf(10, 50) }
				.forEach { assertFalse(a[it]) }
		assertFails { a[100] }
		assertFails { a[101] }
	}
}
