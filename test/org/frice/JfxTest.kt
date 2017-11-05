package org.frice


fun main(args: Array<String>) {
	val obj = object : GameFX() {
		override fun onInit() {
			title = "666"
		}
	}
	launch(obj)
}
