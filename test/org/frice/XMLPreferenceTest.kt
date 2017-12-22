package org.frice

import org.frice.util.data.XMLPreference
import org.junit.BeforeClass
import org.junit.Test

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class XMLPreferenceTest {

	@Test
	fun insert() {
		p.insert("ice", 1000)
		p.insert("lizhaohan", 1)
		p.insert("jelly", "bean")
	}

	@Test
	fun query() {
		println("p.query(\"ice\", 5) = ${p.query("ice", 5)}")
		println("p.query(\"jelly\", jelly) = ${p.query("jelly", "")}")
	}

	companion object Init {
		lateinit var p: XMLPreference

		@BeforeClass
		@JvmStatic
		fun init() {
			p = XMLPreference("./test.xml")
		}
	}
}