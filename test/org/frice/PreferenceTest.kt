package org.frice

import org.frice.utils.data.Preference
import org.frice.utils.message.FLog
import org.junit.Test

/**
 * Created by ice1000 on 2016/8/21.
 * @author ice1000
 * @since v0.4.1
 */
class PreferenceTest {

	private val p = Preference("./test.properties")

	@Test
	fun query() {
		p.insert(KEY, 2333)
	}

	@Test
	fun insert() {
		FLog.v(p.query(KEY, 6666))
	}

	companion object Init {
		val KEY = "fuck"
	}
}