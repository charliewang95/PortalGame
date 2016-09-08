import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * The main class for this game
 * 
 * @author Charlie Wang Modified from code by Robert Duvall
 * 
 */
public class ImpasseMain extends Application {
	private static final int INITLEVEL = 5;  //the beginning level to start the game; use to debug; default-->0
	public static final int SIZE = 600; //size of the board
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 100 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 0.1 / FRAMES_PER_SECOND;
	private Stage myStage;
	public ImpasseGame[] myGame = new ImpasseGame[20];
	private int currentLevel;
	private Scene[] scene = new Scene[20];

	@Override
	public void start(Stage s) throws Exception {
		myStage = s;
		currentLevel=INITLEVEL;
		for (int i = 0; i < 8; i++) {
			myGame[i] = new ImpasseGame();
			s.setTitle(myGame[i].getTitle());
			scene[i] = myGame[i].init(SIZE, SIZE, i);
			if (i==0)
				myGame[0].buttonBegin.setOnAction(e -> ButtonClicked(e));
			else 
				myGame[i].buttonLevel.setOnAction(e -> ButtonClicked(e));
		}
		s.setScene(scene[INITLEVEL]);
		s.setResizable(false);
		s.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> myGame[currentLevel].step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void ButtonClicked(ActionEvent e) {
		if (e.getSource() == myGame[currentLevel].buttonLevel || e.getSource() == myGame[0].buttonBegin) {
			currentLevel++;
			myStage.setScene(scene[currentLevel]);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
