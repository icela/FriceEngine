package org.frice.obj

/**
 * This is just an attacher, add objects inside.
 *
 * @author ice1000
 * @since v1.7.2
 */
class AttachedObjects(val objs: MutableList<FObject>) : MutableList<FObject> by objs {
	constructor(vararg fObject: FObject) : this(fObject.toMutableList())

	/** scale all objects */
	fun scale(x: Double, y: Double) = forEach { it.scale(x, y) }

	/** move all objects */
	fun move(x: Double, y: Double) = forEach { it.move(x, y) }

	/** set all objects' `died` to true */
	fun die() = forEach { it.died = true }

	/** set all objects' `isVisible` to false */
	fun hide() = forEach { it.isVisible = false }

	/** rotate all objects */
	fun rotate(angle: Double) = forEach { it.rotate(angle) }

	/** stop all objects' anims */
	fun stopAnims() = forEach(FObject::stopAnims)

	fun clearDied() = removeIf(FObject::died)
	fun addObject(vararg objects: FObject) = objects.forEach { add(it) }
	fun removeObject(vararg objects: FObject) = objects.forEach { remove(it) }
}

/**
 * This is just an attacher, add objects inside.
 *
 * @author ice1000
 * @since v1.7.2
 * @see AttachedObjects
 */
class AttachedAbstractObjects(val objs: MutableList<AbstractObject>) : MutableList<AbstractObject> by objs {
	constructor(vararg fObject: AbstractObject) : this(fObject.toMutableList())

	/** move all objects */
	fun move(x: Double, y: Double) = forEach { it.move(x, y) }

	/** set all objects' `died` to true */
	fun die() = forEach { it.died = true }

	/** set all objects' `isVisible` to false */
	fun hide() = forEach { it.isVisible = false }

	/** rotate all objects */
	fun rotate(angle: Double) = forEach { it.rotate(angle) }

	fun addObject(vararg objects: AbstractObject) = objects.forEach { add(it) }
	fun clearDied() = removeIf(AbstractObject::died)
	fun removeObject(vararg objects: AbstractObject) = objects.forEach { remove(it) }
}
