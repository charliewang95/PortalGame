import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * The main class for this game
 * @author Charlie Wang
 * Modified from code by Robert Duvall
 * 
 */
public class ImpasseMain extends Application {
	private int INITLEVEL=1;
	public static final int SIZE = 600;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 100 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 0.1 / FRAMES_PER_SECOND;
	private ImpasseGame myGame;
	

	@Override
	public void start(Stage s) throws Exception {
		int currentLevel = INITLEVEL;
		myGame = new ImpasseGame();
		s.setTitle(myGame.getTitle());
		Scene scene = myGame.init(SIZE, SIZE, currentLevel);
		s.setScene(scene);
		s.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> myGame.step(SECOND_DELAY));
		Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
	}

	public static void main (String[] args) {
        launch(args);
    }
}
