import org.frice.game.Game;
import org.frice.game.event.OnClickEvent;
import org.frice.game.obj.ImageObject;
import org.frice.game.resource.image.FileImageResource;
import org.frice.game.resource.image.FrameImageResource;
import org.frice.game.utils.audio.AudioManager;
import org.frice.game.utils.audio.AudioPlayer;
import org.jetbrains.annotations.Nullable;

/**
 * Created by ice1000 on 2016/8/16.
 *
 * @author ice1000
 * @since v0.3.1
 */
public class Demo8 extends Game {

	private AudioPlayer player;

	@Override
	protected void onInit() {
		ImageObject object = new ImageObject(new FrameImageResource(this, new FileImageResource[]{
				new FileImageResource("1.png"),
				new FileImageResource("2.png"),
				new FileImageResource("3.png"),
				new FileImageResource("4.png"),
				new FileImageResource("5.png")}, 1000), 100.0, 100.0);
		addObject(object);
		setCursor(object);
		player = AudioManager.getPlayer("1.wav");
		player.start();
	}

	@Override
	protected void onClick(@Nullable OnClickEvent e) {
		if (player.getPaused()) player.setPaused(false);
		else player.setPaused(true);
	}

	public static void main(String[] args) {
		new Demo8();
	}
}
