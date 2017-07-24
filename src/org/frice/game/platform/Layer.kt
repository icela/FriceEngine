package org.frice.game.platform

import org.frice.game.obj.AbstractObject
import org.frice.game.obj.button.FText
import org.frice.game.utils.message.log.FatalError
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
		is AbstractObject -> objectAddBuffer.add(obj)
		else -> throw FatalError("Type of $obj(${obj::class.java.name}) is neither FText nor AbstractObject")
	}

	fun removeObject(obj: AbstractObject): Boolean = when (obj) {
		is FText -> textDeleteBuffer.add(obj)
		is AbstractObject -> objectDeleteBuffer.add(obj)
		else -> throw FatalError("Type of $obj(${obj::class.java.name}) is neither FText nor AbstractObject")
	}
}