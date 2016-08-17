import kotlin.Pair;
import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnCollideEvent;
import org.frice.game.obj.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.message.FDialog;
import org.frice.game.utils.graphics.shape.FCircle;
import org.frice.game.utils.graphics.shape.FRectangle;
import org.frice.game.utils.time.FTimer;

import java.util.Random;

public class Demo7 extends Game {
	public static void main(String[] args) {
		new Demo7();
	}

	private Random random = new Random();
	private FTimer timer = new FTimer(3000);
	private ShapeObject object;
	private OnCollideEvent gameOver;

	@Override
	protected void onInit() {
		setSize(500, 800);
		setTitle("Flappy bird demo by ice1000");
		object = new ShapeObject(ColorResource.get宝强绿(), new FCircle(20.0), 50.0, 200.0);
		object.getAnims().add(AccelerateMove.getGravity());
		addObject(object);
		gameOver = () -> {
			setStopped(true);
			new FDialog(this).show("Game Over");
			System.exit(0);
		};
	}

	@Override
	protected void onRefresh() {
		if (object.getY() > getHeight() + 20) gameOver.handle();
		if (timer.ended()) addObjects(getObj());
	}

	@Override
	protected void onClick(OnClickEvent e) {
		object.getAnims().clear();
		object.getAnims().add(AccelerateMove.getGravity());
		object.getAnims().add(new SimpleMove(0, -400));
	}

	private ShapeObject[] getObj() {
		int height = random.nextInt(400);
		return new ShapeObject[]{new ShapeObject(ColorResource.get教主黄(),
				new FRectangle(50, height), 550.0, 0.0) {{
			getAnims().add(new SimpleMove(-150, 0));
			getTargets().add(new Pair<>(object, gameOver));
		}}, new ShapeObject(ColorResource.get教主黄(),
				new FRectangle(50, getHeight() - height - 400), 550.0, height + 400.0) {{
			getAnims().add(new SimpleMove(-150, 0));
			getTargets().add(new Pair<>(object, gameOver));
		}}};
	}
}