import org.frice.game.obj.PhysicalObject
import org.frice.game.utils.quad.RectF
import java.util.*

/**
 * QuadTree
 * @author lfkdsk
 * Created by liufengkai on 2016/10/4.
 */
class QuadTreeKT {

	val MAX_OBJECTS = 3

	val MAX_LEVELS = 5

	var level: Int

	private var bounds: RectF

	private val objects: ArrayList<PhysicalObject>

	private val nodes: Array<QuadTreeKT?>


	constructor(level: Int, bounds: RectF) {
		this.level = level
		this.bounds = bounds
	}

	init {
		this.nodes = kotlin.arrayOfNulls<QuadTreeKT>(4)
		this.objects = ArrayList<PhysicalObject>()
	}

	fun clear() {
		objects.clear()
		for (i in nodes.indices) {
			if (nodes[i] != null) {
				nodes[i] = null
			}
		}
	}

	private fun split() {
		// width & height
		val subWidth = (bounds.width() / 2)
		val subHeight = (bounds.height() / 2)
		// x & y
		val x = bounds.left
		val y = bounds.top
		// split to four nodes
		nodes[0] = QuadTreeKT(level + 1, RectF((x + subWidth), y, subWidth, subHeight))
		nodes[1] = QuadTreeKT(level + 1, RectF(x, y, subWidth, subHeight))
		nodes[2] = QuadTreeKT(level + 1, RectF(x, (y + subHeight), subWidth, subHeight))
		nodes[3] = QuadTreeKT(level + 1, RectF((x + subWidth), (y + subHeight), subWidth, subHeight))
	}


	/**
	 * 获取rect 所在的 index

	 * @param rectF 传入对象所在的矩形
	 * *
	 * @return index 使用类别区分所在象限
	 */
	private fun getIndex(rectF: PhysicalObject): Int {
		var index = -1
		val verticalMidpoint = bounds.left + bounds.width() / 2
		val horizontalMidpoint = bounds.top + bounds.height() / 2

		// contain top
		val topQuadrant = rectF.y < horizontalMidpoint && rectF.y + rectF.height < horizontalMidpoint
		// contain bottom
		val bottomQuadrant = rectF.y > horizontalMidpoint

		// contain left
		if (rectF.x < verticalMidpoint && rectF.x + rectF.width < verticalMidpoint) {
			if (topQuadrant) {
				index = 1
			} else if (bottomQuadrant) {
				index = 2
			}
			// contain right
		} else if (rectF.x > verticalMidpoint) {
			if (topQuadrant) {
				index = 0
			} else if (bottomQuadrant) {
				index = 3
			}
		}

		return index
	}

	/**
	 * insert object to tree

	 * @param rectF object
	 */
	fun insert(rectF: PhysicalObject) {
		if (nodes[0] != null) {
			val index = getIndex(rectF)

			if (index != -1) {
				nodes[index]?.insert(rectF)
				return
			}
		}

		objects.add(rectF)

		if (objects.size > MAX_OBJECTS && level < MAX_LEVELS) {
			// don't have subNodes
			// split node
			if (nodes[0] == null) {
				split()
			}

			var i = 0
			while (i < objects.size) {

				val index = getIndex(objects[i])

				if (index != -1) {

					nodes[index]?.insert(objects.removeAt(i))

				} else {

					// don't in subNode save to parent node.
					// eq: object on line
					i++
				}
			}
		}
	}

	/**
	 * return all the object collision with the object

	 * @param returnObjects return list
	 * *
	 * @param rectF         object
	 * *
	 * @return list of collision
	 */
	fun retrieve(returnObjects: ArrayList<ArrayList<PhysicalObject>>, rectF: PhysicalObject): List<List<PhysicalObject>> {
		val index = getIndex(rectF)

		if (index != -1 && nodes[0] != null) {
			nodes[index]?.retrieve(returnObjects, rectF)
		}

		returnObjects.add(objects)
		return returnObjects
	}
}