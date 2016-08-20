package org.frice.game.utils.data

import org.frice.game.Game
import java.io.File

/**
 * Created by ice1000 on 2016/8/20.
 *
 * @author ice1000
 * @since v0.4
 */
class ConstObjectLoader(private val game: Game, private val file: File) {
	constructor(game: Game, path: String) : this(game, File(path))
}