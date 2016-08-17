import org.frice.game.Game;
import org.frice.game.obj.effects.ParticleEffect;
import org.frice.game.resource.graphics.ParticleResource;

/**
 * Created by ice1000 on 2016/8/17.
 *
 * @author ice1000
 * @since 0.3.2
 */
public class Demo9 extends Game {
	@Override
	protected void onInit() {
		super.onInit();
		addObject(new ParticleEffect(new ParticleResource(this, 50, 50), 100, 100));
	}
}
