import kotlin.Pair;
import org.frice.game.Game;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.obj.effects.ParticleEffect;
import org.frice.game.obj.sub.ImageObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.resource.graphics.ParticleResource;
import org.frice.game.resource.image.FileImageResource;
import org.frice.game.utils.audio.AudioManager;
import org.frice.game.utils.time.FTimer;

import java.awt.event.KeyEvent;

public class Demo9 extends Game {
	public static void main(String[] args) {
		new Demo9();
	}

	private ImageObject boss;
	private FTimer timer = new FTimer(600);

	@Override
	protected void onInit() {
		super.onInit();
		boss = new ImageObject(new FileImageResource("3.png"), 10.0, 20.0);
		ImageObject plane = new ImageObject(new FileImageResource("1.png"));
		setBounds(100, 100, 500, 500);
		addObject(new ParticleEffect(new ParticleResource(this, 500, 500,
				ColorResource.getWHITE(), ColorResource.getGREEN()), 0, 0));
		addObject(boss);
		addObject(plane);
		listenKeyPressed(e -> {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					ImageObject object = new ImageObject(new FileImageResource("2.png"), plane.getX(), plane.getY());
					object.getAnims().add(new SimpleMove(0, -300));
					object.getTargets().add(new Pair<>(boss, () -> {
						AudioManager.play("1.wav");
						removeObject(object);
					}));
					addObject(object);
					break;
				case KeyEvent.VK_A:
					plane.setX(plane.getX() - 20);
					break;
				case KeyEvent.VK_D:
					plane.setX(plane.getX() + 20);
					break;
				case KeyEvent.VK_S:
					plane.setY(plane.getY() + 20);
					break;
				case KeyEvent.VK_W:
					plane.setY(plane.getY() - 20);
					break;

			}
		});
	}

	@Override
	protected void onRefresh() {
		if (timer.ended()) {
			boss.getAnims().clear();
			boss.getAnims().add(new SimpleMove(
					getRandom().nextInt(500) - (int) boss.getX(),
					getRandom().nextInt(500) - (int) boss.getY()));
		}
	}
}


// 苟利国家生死以，岂因祸福避趋之！
// 祝长者生日快乐！


