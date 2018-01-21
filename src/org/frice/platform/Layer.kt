package org.frice.platform

import org.frice.obj.AbstractObject
import org.frice.obj.button.FButton
import org.frice.obj.button.FText
import java.util.*

/**
 * @author ice1000
 * @since v1.4.0
 */
class Layer {
	val objects = LinkedList<AbstractObject>()
	private val objectDeleteBuffer = ArrayList<AbstractObject>()
	private val objectAddBuffer = ArrayList<AbstractObject>()

	val texts = LinkedList<FText>()
	private val textDeleteBuffer = ArrayList<FText>()
	private val textAddBuffer = ArrayList<FText>()

	var autoGC = true
	val buttons = LinkedList<FButton>()

	fun processBuffer() {
		objects.addAll(objectAddBuffer)
		objects.removeAll(objectDeleteBuffer)
		objectDeleteBuffer.clear()
		objectAddBuffer.clear()

		texts.addAll(textAddBuffer)
		texts.removeAll(textDeleteBuffer)
		textDeleteBuffer.clear()
		textAddBuffer.clear()

		buttons.removeIf(FButton::died)
	}

	fun clearObjects() {
		objectDeleteBuffer.addAll(objects)
		textDeleteBuffer.addAll(texts)
		buttons.clear()
	}

	fun addObject(obj: AbstractObject): Boolean {
		if (obj is FButton) buttons.add(obj)
		return when (obj) {
			is FText -> textAddBuffer.add(obj)
			else -> objectAddBuffer.add(obj)
		}
	}

	fun removeObject(obj: AbstractObject): Boolean {
		if (obj is FButton) buttons.add(obj)
		return when (obj) {
			is FText -> textDeleteBuffer.add(obj)
			else -> objectDeleteBuffer.add(obj)
		}
	}

	fun instantAddObject(obj: AbstractObject): Boolean {
		if (obj is FButton) buttons.add(obj)
		return when (obj) {
			is FText -> texts.add(obj)
			else -> objects.add(obj)
		}
	}

	fun instantRemoveObject(obj: AbstractObject): Boolean {
		if (obj is FButton) buttons.add(obj)
		return when (obj) {
			is FText -> texts.remove(obj)
			else -> objects.remove(obj)
		}
	}
}