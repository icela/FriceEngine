require 'java'

java_import org.frice.game.Game

class Demo5 < Game
	def onInit
		setBounds java.awt.Rectangle.new 100, 100, 800, 800
		setTitle 'JRuby demo by ice1000'
	end
	def onRefresh
	end
	def onClick(e)
	end
	def onMouse(e)
	end
end

a = Demo5.new
p a.getTitle