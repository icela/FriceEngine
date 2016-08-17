import org.frice.game.Game;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.obj.effects.ParticleEffect;
import org.frice.game.obj.sub.ImageObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.resource.graphics.ParticleResource;
import org.frice.game.resource.image.FileImageResource;
import org.frice.game.utils.time.FTimer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ice1000 on 2016/8/17.
 *
 * @author ice1000
 * @since 0.3.2
 */
public class Demo9 extends Game {
	public static void main(String[] args) {
		new Demo9();
	}

	private ImageObject boss;
	private int x = 250, y = 250;
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
		setCursor(plane);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
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

	@Override
	protected void onClick(OnClickEvent e) {
		x = e.getEvent().getX();
		y = e.getEvent().getY();
	}
}
