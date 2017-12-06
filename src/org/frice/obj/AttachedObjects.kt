package org.frice.obj

/**
 * Don't `addObject(objectGroup)`.
 * This is just an attacher, add objects inside.
 *
 * @author ice1000
 * @since v1.7.2
 */
class AttachedObjects(val objs: MutableList<FObject>) {

	fun scale(x: Double, y: Double) = objs.forEach { it.scale(x, y) }
	fun move(x: Double, y: Double) = objs.forEach { it.move(x, y) }
	fun rotate(angle: Double) = objs.forEach { it.rotate(angle) }

	fun addObject(vararg objects: FObject) = objects.forEach { objs.remove(it) }
	fun removeObject(vararg objects: FObject) = objects.forEach { objs.remove(it) }
}

/**
 * @author ice1000
 * @since v1.7.2
 * @see AttachedObjects
 */
class AttachedAbstarctObjects(val objs: MutableList<AbstractObject>) {
	fun move(x: Double, y: Double) = objs.forEach { it.move(x, y) }

	fun addObject(vararg objects: FObject) = objects.forEach { objs.remove(it) }
	fun removeObject(vararg objects: FObject) = objects.forEach { objs.remove(it) }
}