package org.frice.game.utils.graphics.utils

import org.frice.game.utils.misc.shouldBe
import org.junit.Test
import java.awt.Color
import java.util.*

class ColorUtilsTest {
	@Test
	fun averageTest() {
		Color(0, 0, 0).average() shouldBe 0
		(0..255).forEach { Color(it) shouldBe it }
	}

	@Test
	fun makeColorTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color::class.java.getDeclaredField("value").run {
				isAccessible = true
				get(Color(r, g, b, a)) shouldBe makeColor(r, g, b, a)
			}
		}
	}

	@Test
	fun getAlphaTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color(r, g, b, a).alpha shouldBe a
		}
	}

	@Test
	fun getRedTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color(r, g, b, a).alpha shouldBe a
		}
	}

	@Test
	fun getBlueTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color(r, g, b, a).alpha shouldBe a
		}
	}

	@Test
	fun getGreenTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color(r, g, b, a).alpha shouldBe a
		}
	}

	@Test
	fun greenifyTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color(r, g, b, a).alpha shouldBe a
		}
	}

	@Test
	fun redifyTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color(r, g, b, a).alpha shouldBe a
		}
	}

	@Test
	fun bluifyTest() {
		val rand = Random(System.currentTimeMillis())
		repeat(100) {
			val r = rand.nextInt(255)
			val g = rand.nextInt(255)
			val b = rand.nextInt(255)
			val a = rand.nextInt(255)
			Color(r, g, b, a).alpha shouldBe a
		}
	}

}