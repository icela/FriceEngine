package org.frice.platform

import org.frice.obj.AbstractObject
import org.frice.obj.button.FText
import java.util.*

class Layer {
	val objects = LinkedList<org.frice.obj.AbstractObject>()
	val objectDeleteBuffer = ArrayList<org.frice.obj.AbstractObject>()
	val objectAddBuffer = ArrayList<org.frice.obj.AbstractObject>()

	val texts = LinkedList<org.frice.obj.button.FText>()
	val textDeleteBuffer = ArrayList<org.frice.obj.button.FText>()
	val textAddBuffer = ArrayList<org.frice.obj.button.FText>()

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

	fun addObject(obj: org.frice.obj.AbstractObject): Boolean = when (obj) {
		is org.frice.obj.button.FText -> textAddBuffer.add(obj)
		else -> objectAddBuffer.add(obj)
	}

	fun removeObject(obj: org.frice.obj.AbstractObject): Boolean = when (obj) {
		is org.frice.obj.button.FText -> textDeleteBuffer.add(obj)
		else -> objectDeleteBuffer.add(obj)
	}
}