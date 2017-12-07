package org.frice.obj

/**
 * Don't `addObject(objectGroup)`.
 * This is just an attacher, add objects inside.
 *
 * @author ice1000
 * @since v1.7.2
 */
class AttachedObjects<T : FObject>(val objs: MutableList<T>) {

	fun scale(x: Double, y: Double) = objs.forEach { it.scale(x, y) }
	fun move(x: Double, y: Double) = objs.forEach { it.move(x, y) }
	fun rotate(angle: Double) = objs.forEach { it.rotate(angle) }
	operator fun get(x: Int) = objs[x]

	fun addObject(vararg objects: T) = objects.forEach { objs.remove(it) }
	fun removeObject(vararg objects: T) = objects.forEach { objs.remove(it) }
}

/**
 * @author ice1000
 * @since v1.7.2
 * @see AttachedObjects
 */
class AttachedAbstarctObjects<T : AbstractObject>(val objs: MutableList<T>) {
	fun move(x: Double, y: Double) = objs.forEach { it.move(x, y) }
	operator fun get(x: Int) = objs[x]

	fun addObject(vararg objects: T) = objects.forEach { objs.remove(it) }
	fun removeObject(vararg objects: T) = objects.forEach { objs.remove(it) }
}