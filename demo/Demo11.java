import org.frice.game.Game;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.graphics.shape.FRectangle;
import org.frice.game.utils.time.FTimeListener;

/**
 * An awesome demo
 *
 * @author SuperSodaSea
 */
public class Demo11 extends Game {

	private ColorResource colors[];
	private double a;
	private double b;

	@Override
	public void onInit() {
		setSize(640, 480);
		setTitle("IAmSoSquare Demo");
//		setAutoGC(false);
		setBack(ColorResource.Companion.getBLACK());
		colors = new ColorResource[]{
				ColorResource.Companion.get东条希(),
				ColorResource.Companion.get冰封绿(),
				ColorResource.Companion.get南小鸟(),
				ColorResource.Companion.get吾王蓝(),
				ColorResource.Companion.get园田海未(),
				ColorResource.Companion.get基佬紫(),
				ColorResource.Companion.get如果奇迹有颜色那么一定是橙色(),
				ColorResource.Companion.get宝强绿(),
				ColorResource.Companion.get小埋色(),
				ColorResource.Companion.get小泉花阳(),
				ColorResource.Companion.get教主黄(),
				ColorResource.Companion.get星空凛(),
				ColorResource.Companion.get洵濑绘理(),
				ColorResource.Companion.get矢泽妮可(),
				ColorResource.Companion.get西木野真姬(),
				ColorResource.Companion.get高坂穗乃果(),
		};
		a = 0;
		b = 0;
		addTimeListener(new FTimeListener(1, () -> {
			a += 0.0002;
			b += a;
			addObject(new ShapeObject(colors[(int) (System.currentTimeMillis() / 100 % colors.length)],
					new FRectangle(4, 4), getWidth() / 2, getHeight() / 2) {{
				getAnims().add(new SimpleMove((int) (Math.sin(b) * 256), (int) (Math.cos(b) * 256)));
			}});
		}));
	}

	public static void main(String[] args) {
		new Demo11();
	}
}
