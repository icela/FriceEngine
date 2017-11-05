package org.frice.platform

import org.frice.obj.AbstractObject
import org.frice.obj.button.FText
import java.util.*

class Layer {
	val objects = LinkedList<AbstractObject>()
	val objectDeleteBuffer = ArrayList<AbstractObject>()
	val objectAddBuffer = ArrayList<AbstractObject>()

	val texts = LinkedList<FText>()
	val textDeleteBuffer = ArrayList<FText>()
	val textAddBuffer = ArrayList<FText>()

	fun processBuffer() {
		objects.addAll(objectAddBuffer)
		objects.removeAll(objectDeleteBuffer)
		objectDeleteBuffer.clear()
		objectAddBuffer.clear()

		texts.addAll(textAddBuffer)
		texts.removeAll(textDeleteBuffer)
		textDeleteBuffer.clear()
		textAddBuffer.clear()
	}

	fun clearObjects() {
		objectDeleteBuffer.addAll(objects)
		textDeleteBuffer.addAll(texts)
	}

	fun addObject(obj: AbstractObject): Boolean = when (obj) {
		is FText -> textAddBuffer.add(obj)
		else -> objectAddBuffer.add(obj)
	}

	fun removeObject(obj: AbstractObject): Boolean = when (obj) {
		is FText -> textDeleteBuffer.add(obj)
		else -> objectDeleteBuffer.add(obj)
	}
}