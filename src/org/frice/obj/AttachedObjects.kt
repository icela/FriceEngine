package org.frice.obj

/**
 * This is just an attacher, add objects inside.
 *
 * @author ice1000
 * @since v1.7.2
 */
class AttachedObjects(val objs: MutableList<FObject>) {

	/** scale all objects */
	fun scale(x: Double, y: Double) = objs.forEach { it.scale(x, y) }

	/** move all objects */
	fun move(x: Double, y: Double) = objs.forEach { it.move(x, y) }

	/** set all objects' `died` to true */
	fun die() = objs.forEach { it.died = true }

	/** set all objects' `isVisible` to true */
	fun hide() = objs.forEach { it.isVisible = false }

	/** rotate all objects */
	fun rotate(angle: Double) = objs.forEach { it.rotate(angle) }

	operator fun get(x: Int) = objs[x]

	/** stop all objects' anims */
	fun stopAnims() = objs.forEach { it.stopAnims() }

	fun addObject(vararg objects: FObject) = objects.forEach { objs.add(it) }
	fun removeObject(vararg objects: FObject) = objects.forEach { objs.remove(it) }
	fun size() = objs.size
}

/**
 * This is just an attacher, add objects inside.
 *
 * @author ice1000
 * @since v1.7.2
 * @see AttachedObjects
 */
class AttachedAbstarctObjects(val objs: MutableList<AbstractObject>) {
	/** move all objects */
	fun move(x: Double, y: Double) = objs.forEach { it.move(x, y) }

	/** set all objects' `died` to true */
	fun die() = objs.forEach { it.died = true }

	/** set all objects' `isVisible` to true */
	fun hide() = objs.forEach { it.isVisible = false }

	/** rotate all objects */
	fun rotate(angle: Double) = objs.forEach { it.rotate(angle) }

	operator fun get(x: Int) = objs[x]

	fun addObject(vararg objects: AbstractObject) = objects.forEach { objs.add(it) }
	fun removeObject(vararg objects: AbstractObject) = objects.forEach { objs.remove(it) }
	fun size() = objs.size
}
