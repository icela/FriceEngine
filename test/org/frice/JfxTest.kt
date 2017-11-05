package org.frice


fun main(args: Array<String>) {
	val obj = O()
	launch(obj)
}

class O : GameFX() {
	override fun onInit() {
		title = "666"
	}
}
