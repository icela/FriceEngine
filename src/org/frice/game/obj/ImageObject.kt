package org.frice.game.obj

import org.frice.game.anim.FAnim
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.shape.FRectangle
import java.awt.Image
import java.awt.image.BufferedImage
import java.util.*

/**
 * Base GameObject class
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class ImageObject(var res: ImageResource, override var id: Int,
                       override var x: Double, override var y: Double) : FObject {
	constructor(res: ImageResource, id: Int) : this(res, id, 0.0, 0.0)

	constructor(res: ImageResource) : this(res, -1, 0.0, 0.0)

	constructor(res: ImageResource, x: Double, y: Double) : this(res, -1, x, y)

	override fun getResource() = res

	// TODO
	override fun isCollide(other: FObject) = false

	override val anims: ArrayList<FAnim> = ArrayList()
	override val shape = FRectangle(res.image.width, res.image.height)

	override fun scale(p: Pair<Double, Double>) {
		res.image = res.image.getScaledInstance((res.image.width * p.first).toInt(),
				(res.image.height * p.second).toInt(), Image.SCALE_DEFAULT) as BufferedImage
	}

	override fun move(p: Pair<Double, Double>) {
		x += p.first
		y += p.second
	}

	/**
	 * @return returns an image instance of this res
	 */
	fun getImage() = res.image

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ImageObject) return false
		if ((id != -1 && id == other.id) ||
				res == other.res && x == other.x && y == other.y) return true
		return false
	}

	override fun hashCode(): Int {
		var result = res.hashCode()
		result = 31 * result + id
		result = 31 * result + x.hashCode()
		result = 31 * result + y.hashCode()
		result = 31 * result + anims.hashCode()
		return result
	}
}