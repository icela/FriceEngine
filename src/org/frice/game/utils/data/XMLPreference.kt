package org.frice.game.utils.data

import org.frice.game.utils.message.log.FatalError
import org.frice.game.utils.misc.forceGet
import org.frice.game.utils.misc.forceLoop
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * An Android-like XMLPreference.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since 0.2.2
 */
class XMLPreference constructor(val file: File) : Database {
	constructor(path: String) : this(File(path))

	private val builder: DocumentBuilder = DocumentBuilderFactory
			.newInstance()
			.newDocumentBuilder()
	private val doc: Document
	private val root: Element

	companion object {
		@JvmField
		val ROOT = "PREFERENCE_CONST_ROOT"
		@JvmField
		val TYPE = "PREFERENCE_CONST_TYPE"
		@JvmField
		val VALUE = "PREFERENCE_CONST_VALUE"

		@JvmField
		val TYPE_BYTE = "PREFERENCE_CONST_TYPE_BYTE"
		@JvmField
		val TYPE_INT = "PREFERENCE_CONST_TYPE_INT"
		@JvmField
		val TYPE_LONG = "PREFERENCE_CONST_TYPE_LONG"
		@JvmField
		val TYPE_SHORT = "PREFERENCE_CONST_TYPE_SHORT"
		@JvmField
		val TYPE_DOUBLE = "PREFERENCE_CONST_TYPE_DOUBLE"
		@JvmField
		val TYPE_FLOAT = "PREFERENCE_CONST_TYPE_FLOAT"
		@JvmField
		val TYPE_STRING = "PREFERENCE_CONST_TYPE_STRING"
		@JvmField
		val TYPE_CHAR = "PREFERENCE_CONST_TYPE_CHAR"
	}

	init {
		if (file.exists()) {
			doc = builder.parse(file)
			root = doc.documentElement
		} else {
			file.createNewFile()
			doc = builder.newDocument()
			root = doc.createElement(ROOT)
			doc.appendChild(root)
			save()
		}
	}

	private fun save() {
		val transformer = TransformerFactory.newInstance().newTransformer()
		transformer.transform(DOMSource(doc), StreamResult(file))
	}

	override fun insert(key: String, value: Any?) = value.let {
		forceLoop { root.removeChild(doc.getElementsByTagName(key).item(0)) }
		val node = doc.createElement(key)
		node.setAttribute(VALUE, value.toString())
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
		save()
	}

	override fun <T> query(key: String, default: T): T {
		val node = doc.getElementsByTagName(key).item(0)
		val value: String?
		try {
			value = node.attributes.getNamedItem(VALUE).nodeValue
		} catch (e: Throwable) {
			return default
		}
		return forceGet(default) {
			when (node.attributes.getNamedItem(TYPE).nodeValue) {
				TYPE_BYTE -> value.toByte() as T
				TYPE_INT -> value.toInt() as T
				TYPE_LONG -> value.toLong() as T
				TYPE_SHORT -> value.toShort() as T
				TYPE_CHAR -> value[0] as T
				TYPE_FLOAT -> value.toFloat() as T
				TYPE_DOUBLE -> value.toDouble() as T
				TYPE_STRING -> value as T
				else -> default
			}
		}
	}
}