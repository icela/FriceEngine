package org.frice.game.utils.web

import org.frice.game.utils.misc.loop

/**
 * HTML tags finder
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */

fun findTag(html: String, tag: CharArray): MutableList<String> {
	val c = html.toCharArray()
	val tags = mutableListOf<String>()
	var tagMark = false
	var tagStart = 0
	loop(c.size - tag.size + 2) { i ->
		// find start index
		if (c[i] == '<') {
			tagMark = true
			// cannot use loop{} or forEach.
			// for the reason that I have to break it
			for (j in 0..tag.size - 1) {
				if (c[i + j + 1] == tag[j]) {
					tagMark = false
					break
				}
			}
			if (tagMark) tagStart = i
		}
		// find end index
		if (tagMark && c[i] == '>') {
			tagMark = false
			tags.add(html.substring(tagStart..i))
		}
	}
	return tags
}

fun findTag(html: String, tag: String) = findTag(html, tag.toCharArray())
