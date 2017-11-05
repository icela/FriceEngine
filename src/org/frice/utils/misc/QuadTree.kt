package org.frice.utils.misc

import org.frice.obj.PhysicalObject
import org.frice.utils.shape.FShapeDouble
import java.util.*

/**
 * QuadTree
 * Created by liufengkai on 2016/10/4.
 * Some fixes by ice1000
 *
 * @author lfkdsk
 */
class QuadTree(private var level: Int, private var bounds: FQuad) {
	class FQuad(var x: Double, var y: Double, override var width: Double, override var height: Double) : FShapeDouble

	private val maxObjects = 3
	private val maxLevels = 5

	private val objects = arrayListOf<PhysicalObject>()

	private val nodes = arrayOfNulls<QuadTree>(4)

	fun clear() {
		objects.clear()
		for (i in nodes.indices) nodes[i] = null
	}

	private fun split() {
		// width & height
		val subWidth = bounds.width / 2
		val subHeight = bounds.height / 2
		// x & y
		val x = bounds.x
		val y = bounds.y
		// split to four nodes
		nodes[0] = QuadTree(level + 1, FQuad(x + subWidth, y, subWidth, subHeight))
		nodes[1] = QuadTree(level + 1, FQuad(x, y, subWidth, subHeight))
		nodes[2] = QuadTree(level + 1, FQuad(x, subWidth, (y + subHeight), subHeight))
		nodes[3] = QuadTree(level + 1, FQuad(x + subWidth, y + subHeight, subWidth, subHeight))
	}


	/**
	 * 获取rect 所在的 index
	 *
	 * @param rectF 传入对象所在的矩形
	 * @return index 使用类别区分所在象限
	 */
	private fun getIndex(rectF: PhysicalObject): Int {
		var index = -1
		val verticalMidpoint = bounds.x + bounds.width / 2
		val horizontalMidpoint = bounds.y + bounds.height / 2

		// contain top
		val topQuadrant = rectF.y < horizontalMidpoint && rectF.y + rectF.height < horizontalMidpoint
		// contain bottom
		val bottomQuadrant = rectF.y > horizontalMidpoint

		// contain left
		if (rectF.x < verticalMidpoint && rectF.x + rectF.width < verticalMidpoint) {
			if (topQuadrant) index = 1
			else if (bottomQuadrant) index = 2
			// contain right
		} else if (rectF.x > verticalMidpoint) {
			if (topQuadrant) index = 0
			else if (bottomQuadrant) index = 3
		}

		return index
	}

	/**
	 * insert object to tree
	 *
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

		objects += rectF

		if (objects.size > maxObjects && level < maxLevels) {
			// don't have subNodes
			// split node
			if (nodes[0] == null) split()

			var i = 0
			while (i < objects.size) {
				val index = getIndex(objects[i])
				if (index != -1) nodes[index]?.insert(objects.removeAt(i))
				// don't in subNode save to parent node.
				// eq: object on line
				else i++
			}
		}
	}

	/**
	 * return all the object collision with the object

	 * @param returnObjects return list
	 * @param rectF         object
	 * @return list of collision
	 */
	fun retrieve(
			returnObjects: ArrayList<List<PhysicalObject>>,
			rectF: PhysicalObject): List<List<PhysicalObject>> {
		val index = getIndex(rectF)
		if (-1 != index && null != nodes[0]) nodes[index]?.retrieve(returnObjects, rectF)
		returnObjects += objects
		return returnObjects
	}
}
