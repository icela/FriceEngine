package org.frice.game

import org.frice.game.utils.data.Preference
import org.frice.game.utils.message.FLog
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
		FLog.i(p.query(KEY, 6666))
	}

	companion object Init {
		val KEY = "fuck"
	}
}