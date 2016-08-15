package org.frice.game.utils.data

import org.frice.game.utils.message.error.FatalError
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * An Android-like Preference.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since 0.2.2
 */
class Preference(val file: File) {
	constructor(path: String) : this(File(path))

	private val builder: DocumentBuilder
	private val doc: Document
	private val root: Element

	companion object {
		private val ROOT = "PREFERENCE_CONST_ROOT"
		private val TYPE = "PREFERENCE_CONST_TYPE"
		private val VALUE = "PREFERENCE_CONST_VALUE"
		
		private val TYPE_BYTE = "PREFERENCE_CONST_TYPE_BYTE"
		private val TYPE_INT = "PREFERENCE_CONST_TYPE_INT"
		private val TYPE_LONG = "PREFERENCE_CONST_TYPE_LONG"
		private val TYPE_SHORT = "PREFERENCE_CONST_TYPE_SHORT"
		private val TYPE_DOUBLE = "PREFERENCE_CONST_TYPE_DOUBLE"
		private val TYPE_FLOAT = "PREFERENCE_CONST_TYPE_FLOAT"
		private val TYPE_STRING = "PREFERENCE_CONST_TYPE_STRING"
		private val TYPE_CHAR = "PREFERENCE_CONST_TYPE_CHAR"
	}

	init {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		if (!file.exists()) {
			file.createNewFile()
			doc = builder.newDocument()
			root = doc.createElement(ROOT)
			doc.appendChild(root)
		} else {
			doc = builder.parse(file)
			root = doc.getElementById(ROOT)
		}
	}

	private fun save() {
		val transformer = TransformerFactory.newInstance().newTransformer()
		transformer.transform(DOMSource(doc), StreamResult(file))
	}

	fun insert(key: String, value: Any?) = value.let {
		val node = doc.createElement(key)
		node.nodeValue = value.toString()
		node.setAttribute(TYPE, when (value) {
			is Byte -> TYPE_BYTE
			is Int -> TYPE_INT
			is Long -> TYPE_LONG
			is Short -> TYPE_SHORT
			is Float -> TYPE_FLOAT
			is Double -> TYPE_DOUBLE
			is Char -> TYPE_CHAR
			is String -> TYPE_STRING
			else -> throw FatalError("invalid type!")
		})
		root.appendChild(node)
	}
}