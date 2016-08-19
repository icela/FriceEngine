import org.frice.game.Game;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.graphics.shape.FCircle;
import org.frice.game.utils.time.FTimeListener;

import java.awt.*;

/**
 * An awesome demo
 *
 * @author SuperSodaSea
 */
public class Demo11 extends Game {

	private ColorResource colors[];
	private double a = 0;
	private double b = 0;

	@Override
	public void onInit() {
		new Color(0xffffff);
		colors = new ColorResource[]{
				ColorResource.Companion.get东条希(),
				ColorResource.Companion.get南小鸟(),
				ColorResource.Companion.get园田海未(),
				ColorResource.Companion.get小泉花阳(),
				ColorResource.Companion.get星空凛(),
				ColorResource.Companion.get洵濑绘理(),
				ColorResource.Companion.get矢泽妮可(),
				ColorResource.Companion.get西木野真姬(),
				ColorResource.Companion.get高坂穗乃果(),
		};
		setSize(1200, 720);
		setTitle("IAmSoSquare Demo");
		setBack(ColorResource.Companion.getBLACK());
		addTimeListener(new FTimeListener(1, () -> {
			a += 0.0002;
			b += a;
			addObject(new ShapeObject(colors[(int) (System.currentTimeMillis() / 100 % colors.length)],
					new FCircle(2), getWidth() / 2, getHeight() / 2) {{
				getAnims().add(new SimpleMove((int) (Math.sin(b) * 256), (int) (Math.cos(b) * 256)));
			}});
		}));
	}

	public static void main(String[] args) {
		new Demo11();
	}
}
