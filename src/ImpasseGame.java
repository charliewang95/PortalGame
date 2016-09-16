import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Game class. When called, set the scene for the required level. To go to the
 * next level, press the next level button, and the control will give back to
 * the Main class to transit scenes.
 * 
 * NOTE:Some magic numbers in the level setup methods (level0-level8) are
 * obtained after multiple trials and error. Although its a violation of the
 * checklist rules, I can't think of a way to efficiently represent all the
 * specific locations in variable names. Now all the objects are at the perfect
 * spot.
 * 
 * @author Charlie Wang
 * 
 */
public class ImpasseGame {
	private static final String TITLE = "Impasse";
	private static final int KEY_INPUT_SPEED = 15; // the moving speed of the
													// green ball
	private boolean CANGOTONEXTLEVEL = false; // changing this will change
												// whether the player can go to
												// the
												// next level without finishing
												// the current one
	private static final int RADIUS = 15; // the radius of the balls
	private static final Color ballColor = Color.GREEN;
	private static final Color destColor = Color.RED;
	private int myLevel, ballInitX, ballInitY;
	private Scene myScene;
	private boolean cheat;
	private ArrayList<Rectangle> myWall = new ArrayList<Rectangle>();
	private ArrayList<Portal> myPortal = new ArrayList<Portal>();
	private Circle myBall, myDest;
	private Group root;

	public Button buttonLevel, buttonBegin, buttonReset;
	private Text winText, solutionText, cheatText, levelName;

	/**
	 * @return the title of the game
	 */
	public String getTitle() {
		return TITLE;
	}

	/**
	 * Initialize the board, draws the map, walls, portals, doors, and balls
	 */
	public Scene init(int width, int height, int level) {
		myLevel = level;
		myWall.clear();
		myPortal.clear();
		root = new Group();
		myScene = new Scene(root, width, height, Color.WHITE);
		myScene.setFill(Color.LIGHTGRAY);

		Image image = new Image(getClass().getClassLoader().getResourceAsStream("light_texture2291.jpg"));
		ImageView iv = new ImageView(image);
		root.getChildren().add(iv);

		// write all the stuff on the screen
		writeLevelName();
		writeAuthor();
		writeSolutionText();
		writeHelpText();
		writeCheatText();
		writeWinMessage();

		// Set up the board according to which level I'm in
		chooseLevel();

		for (Rectangle r : myWall) {
			r.setFill(Color.BLACK);
			root.getChildren().add(r);
		}

		for (Portal p : myPortal) {
			root.getChildren().add(p.geth1().getRec());
			root.getChildren().add(p.geth2().getRec());
			for (Door d : p.getAllDoors()) {
				root.getChildren().add(d.getDoor());
				root.getChildren().addAll(d.getFakeDoor());
			}
		}
		if (myLevel != 0) {
			myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		}
		return myScene;
	}

	public void chooseLevel() {
		if (myLevel == 1)
			level1();
		else if (myLevel == 2)
			level2();
		else if (myLevel == 3)
			level3();
		else if (myLevel == 4)
			level4();
		else if (myLevel == 5)
			level5();
		else if (myLevel == 6)
			level6();
		else if (myLevel == 7)
			level7();
		else if (myLevel == 8)
			level8();
		else
			level0();
	}

	private void writeLevelName() {
		if (myLevel != 0) {
			drawNextButton();

			Text tLevel = new Text("Level " + myLevel);
			tLevel.setX(270);
			tLevel.setY(60);
			root.getChildren().add(tLevel);
			tLevel.setFont(Font.font("Verdana", 20));
			tLevel.setFill(Color.BLACK);

			levelName = new Text();
			levelName.setY(85);
			root.getChildren().add(levelName);
			levelName.setFont(Font.font("Verdana", 20));
			levelName.setFill(Color.BLACK);
		}
	}

	private void writeAuthor() {
		Text tAuthor = new Text("Created by Charlie Wang");
		tAuthor.setX(410);
		tAuthor.setY(560);
		tAuthor.setFont(Font.font("Verdana", 12));
		root.getChildren().add(tAuthor);
	}

	private void writeSolutionText() {
		solutionText = new Text();
		solutionText.setX(40);
		solutionText.setY(40);
		solutionText.setFont(Font.font("Verdana", 12));
		solutionText.setVisible(false);
		root.getChildren().add(solutionText);
	}

	private void writeHelpText() {
		Text tGoal = new Text("Control the green ball to reach the red ball");
		Text tKey = new Text("Press \u2190, \u2191, \u2192, \u2193 to move");
		Text tReset = new Text("Press \"R\" to reset the level");
		Text tHint = new Text("Press \"H\" to show/hide the solution");
		tGoal.setX(50);
		tGoal.setY(535);
		tKey.setX(50);
		tKey.setY(550);
		tReset.setX(50);
		tReset.setY(565);
		tHint.setX(50);
		tHint.setY(580);
		tGoal.setFont(Font.font("Verdana", 12));
		tReset.setFont(Font.font("Verdana", 12));
		tHint.setFont(Font.font("Verdana", 12));
		tKey.setFont(Font.font("Verdana", 12));
		root.getChildren().add(tGoal);
		root.getChildren().add(tReset);
		root.getChildren().add(tHint);
		root.getChildren().add(tKey);
	}

	private void writeCheatText() {
		cheatText = new Text("Cheat Activated");
		cheatText.setX(257);
		cheatText.setY(35);
		cheatText.setFont(Font.font("Verdana", 12));
		cheatText.setVisible(false);
		root.getChildren().add(cheatText);
	}

	/**
	 * Print the winning message
	 */
	private void writeWinMessage() {
		DropShadow ds = new DropShadow();
		ds.setOffsetY(12.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		winText = new Text();
		winText.setEffect(ds);
		winText.setX(80.0f);
		winText.setY(300.0f);
		winText.setFill(Color.RED);
		winText.setText("Congratulations!");
		winText.setFont(Font.font("Serif", 68));
		root.getChildren().add(winText);
		winText.setVisible(false);
	}

	private void drawNextButton() {
		buttonLevel = new Button("Next Level");
		buttonLevel.setLayoutX(450);
		buttonLevel.setLayoutY(40);
		root.getChildren().add(buttonLevel);
		buttonLevel.setVisible(CANGOTONEXTLEVEL);
	}

	/**
	 * Show Main screen
	 */
	private void level0() {

		DropShadow ds = new DropShadow();
		ds.setOffsetY(8.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		Text tTitle = new Text();
		tTitle.setEffect(ds);
		tTitle.setX(90.0f);
		tTitle.setY(170.0f);
		tTitle.setFill(Color.RED);
		tTitle.setText("IMPASSE");
		tTitle.setFont(Font.font(null, FontWeight.BOLD, 90));
		root.getChildren().add(tTitle);
		
		Text tTitle2 = new Text();
		tTitle2.setX(350.0f);
		tTitle2.setY(220.0f);
		tTitle2.setFill(Color.BLACK);
		tTitle2.setText("-- A Puzzle Game");
		tTitle2.setFont(Font.font(null, FontWeight.BOLD, 20));
		root.getChildren().add(tTitle2);

		buttonBegin = new Button("New Game");
		buttonBegin.setLayoutX(260);
		buttonBegin.setLayoutY(320);
		buttonBegin.setScaleX(3);
		buttonBegin.setScaleY(3);
		root.getChildren().add(buttonBegin);

	}

	/**
	 * Map setup of level 1 Sol: O
	 * 
	 * NOTE: In each level setup,
	 */
	private void level1() {
		/*
		 * The procedure for each level setup method are similar, so only this
		 * method has comments.
		 */

		// draw walls
		myWall.add(new Rectangle(150, 250, 300, 5));
		myWall.add(new Rectangle(150, 350, 300, 5));
		myWall.add(new Rectangle(150, 250, 5, 100));
		myWall.add(new Rectangle(450, 250, 5, 105));
		myWall.add(new Rectangle(275, 250, 20, 100));
		myWall.add(new Rectangle(310, 250, 20, 105));

		// add portals and doors
		Portal portal1 = new Portal(270, 280, 325, 280, 1, 1, Color.ORANGE, 255, 300, 350, 300);
		myPortal.add(portal1);

		// write hints
		Text t1 = new Text("A portal wil transport you to another place...");
		t1.setX(160);
		t1.setY(180);
		t1.setScaleX(1.5);
		t1.setScaleY(1.5);
		t1.setFill(Color.GREEN);
		root.getChildren().add(t1);

		// set level names
		levelName.setText("The Portal");
		levelName.setX(253);

		// set player location and destination location
		setBall(175, 300);
		setDest(425, 300);

		// show solution
		solutionText.setText("Solution: Orange");
	}

	/**
	 * Map setup of level 2 Sol: PU-->GR-->BL
	 */
	private void level2() {

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 100, 5));
		myWall.add(new Rectangle(500, 200, 5, 300));
		myWall.add(new Rectangle(200, 100, 5, 400));
		myWall.add(new Rectangle(200, 200, 300, 5));
		myWall.add(new Rectangle(100, 300, 100, 5));
		myWall.add(new Rectangle(300, 200, 5, 300));
		myWall.add(new Rectangle(300, 300, 200, 5));
		myWall.add(new Rectangle(400, 300, 5, 200));
		myWall.add(new Rectangle(200, 400, 100, 5));

		Portal portal1 = new Portal(125, 100, 495, 225, 0, 1, Color.ORANGE, 150, 125, 480, 250);
		Portal portal2 = new Portal(225, 495, 495, 325, 0, 1, Color.BLUE, 250, 480, 480, 350);
		Portal portal3 = new Portal(225, 200, 495, 425, 0, 1, Color.GREY, 250, 225, 480, 450);
		Portal portal4 = new Portal(425, 200, 100, 325, 0, 1, Color.BROWN, 450, 225, 125, 350);
		Portal portal5 = new Portal(325, 200, 325, 495, 0, 0, Color.PINK, 350, 225, 350, 480);
		Portal portal6 = new Portal(195, 125, 125, 495, 1, 0, Color.PURPLE, 180, 150, 150, 480);
		Portal portal7 = new Portal(100, 425, 425, 495, 1, 0, Color.LIGHTGREEN, 125, 450, 450, 480);
		myPortal.add(portal1);
		myPortal.add(portal2);
		myPortal.add(portal3);
		myPortal.add(portal4);
		myPortal.add(portal5);
		myPortal.add(portal6);
		myPortal.add(portal7);

		Text t1 = new Text("Only portals of the same \ncolor are connected...");
		t1.setX(290);
		t1.setY(140);
		t1.setScaleX(1.5);
		t1.setScaleY(1.5);
		t1.setFill(Color.GREEN);
		root.getChildren().add(t1);

		levelName.setText("The Wall");
		levelName.setX(261);

		setBall(150, 200);
		setDest(250, 450);

		solutionText.setText("Solution: Purple -> \nGreen -> Blue");
	}

	/**
	 * Map setup of level 3 Sol: O
	 */
	private void level3() {

		myWall.add(new Rectangle(150, 150, 5, 300));
		myWall.add(new Rectangle(150, 450, 300, 5));
		myWall.add(new Rectangle(150, 150, 100, 5));
		myWall.add(new Rectangle(250, 150, 5, 200));
		myWall.add(new Rectangle(250, 350, 200, 5));
		myWall.add(new Rectangle(450, 350, 5, 105));
		myWall.add(new Rectangle(250, 350, 5, 105));

		Portal portal1 = new Portal(175, 150, 445, 375, 0, 1, Color.ORANGE, 200, 175, 430, 400);
		portal1.addDoor(155, 350, 95, 5, true);
		portal1.addDoor(350, 355, 5, 95, false);
		myPortal.add(portal1);

		setBall(300, 400);
		setDest(200, 400);

		solutionText.setText("Solution: Orange");

		Text t1 = new Text("When you pass \nthrough a portal...");
		t1.setX(360);
		t1.setY(240);
		t1.setScaleX(2.2);
		t1.setScaleY(2.2);
		t1.setFill(Color.GREEN);
		root.getChildren().add(t1);
		Text t2 = new Text("Closed doors \nof the same color \nwill open...");
		t2.setX(30);
		t2.setY(340);
		t2.setScaleX(1.2);
		t2.setScaleY(1.2);
		t2.setFill(Color.GREEN);
		root.getChildren().add(t2);
		Text t3 = new Text("...and open ones \nwill close");
		t3.setX(290);
		t3.setY(500);
		t3.setScaleX(1.5);
		t3.setScaleY(1.5);
		t3.setFill(Color.GREEN);
		root.getChildren().add(t3);
		levelName.setText("The Door");
		levelName.setX(261);

	}

	/**
	 * Map setup of level 4 Sol: B-->OL-->B
	 */
	private void level4() {

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		myWall.add(new Rectangle(200, 200, 5, 300));
		myWall.add(new Rectangle(300, 200, 200, 5));

		Portal portal1 = new Portal(100, 125, 495, 125, 1, 1, Color.ORANGE, 125, 150, 480, 150);
		portal1.addDoor(105, 320, 95, 5, true);
		portal1.addDoor(200, 105, 5, 95, false);
		myPortal.add(portal1);
		Portal portal2 = new Portal(100, 225, 495, 325, 1, 1, Color.BLUE, 125, 250, 480, 350);
		portal2.addDoor(105, 370, 95, 5, false);
		portal2.addDoor(300, 105, 5, 95, true);
		myPortal.add(portal2);

		levelName.setText("The Deadend");
		levelName.setX(240);

		setBall(450, 450);
		setDest(150, 450);

		solutionText.setText("Solution: Blue -> \nLeft Orange -> Blue");
	}

	/**
	 * Map setup of level 5 Sol: O-->OR-->B-->BU-->P-->PL
	 */
	private void level5() {

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		myWall.add(new Rectangle(275, 275, 5, 50));
		myWall.add(new Rectangle(275, 325, 50, 5));
		myWall.add(new Rectangle(325, 275, 175, 5));

		Portal portal1 = new Portal(100, 150, 495, 150, 1, 1, Color.ORANGE, 125, 175, 480, 175);
		portal1.addDoor(275, 105, 5, 170, true);
		portal1.addDoor(325, 105, 5, 170, true);
		portal1.addDoor(105, 325, 170, 5, false);
		portal1.addDoor(280, 275, 45, 5, false);
		myPortal.add(portal1);
		Portal portal2 = new Portal(150, 100, 150, 495, 0, 0, Color.BLUE, 175, 125, 175, 480);
		portal2.addDoor(105, 275, 170, 5, true);
		portal2.addDoor(325, 325, 5, 175, false);
		myPortal.add(portal2);
		Portal portal3 = new Portal(100, 425, 495, 425, 1, 1, Color.PINK, 125, 450, 480, 450);
		portal3.addDoor(275, 330, 5, 170, true);
		portal3.addDoor(325, 325, 175, 5, false);
		portal3.addDoor(325, 280, 5, 45, false);
		myPortal.add(portal3);

		levelName.setText("The Windmill");
		levelName.setX(240);

		setBall(400, 175);
		setDest(303, 300);

		solutionText.setText("Solution: Orange -> \nRight Orange -> Blue -> \nUpper Blue -> Pink -> \nLeft Pink");
	}

	/**
	 * Map setup of level 6 Sol: O-->BL-->PI-->G-->PU-->BL-->BR-->BL-->BR
	 */
	private void level6() {

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));

		Portal portal1 = new Portal(100, 125, 495, 425, 1, 1, Color.ORANGE, 125, 150, 480, 450);
		Portal portal2 = new Portal(100, 425, 435, 495, 1, 0, Color.BLUE, 125, 450, 460, 480);
		Portal portal3 = new Portal(125, 495, 435, 100, 0, 0, Color.PINK, 150, 480, 460, 125);
		Portal portal4 = new Portal(495, 125, 285, 100, 1, 0, Color.GREY, 480, 150, 310, 125);
		Portal portal5 = new Portal(125, 100, 495, 280, 0, 1, Color.PURPLE, 150, 125, 480, 305);
		Portal portal6 = new Portal(280, 495, 100, 280, 0, 1, Color.BROWN, 305, 480, 125, 305);
		myPortal.add(portal1);
		myPortal.add(portal2);
		myPortal.add(portal3);
		myPortal.add(portal4);
		myPortal.add(portal5);
		myPortal.add(portal6);

		portal1.addDoor(105, 190, 395, 5, true);
		portal2.addDoor(105, 210, 395, 5, true);
		portal6.addDoor(105, 230, 395, 5, true);
		portal3.addDoor(105, 250, 395, 5, true);

		portal5.addDoor(105, 350, 395, 5, true);
		portal4.addDoor(105, 370, 395, 5, true);
		portal1.addDoor(105, 390, 395, 5, true);
		portal6.addDoor(105, 410, 395, 5, false);

		portal1.addDoor(190, 105, 5, 395, true);
		portal2.addDoor(210, 105, 5, 395, true);
		portal3.addDoor(230, 105, 5, 395, true);
		portal4.addDoor(250, 105, 5, 395, true);

		portal5.addDoor(350, 105, 5, 395, true);
		portal6.addDoor(375, 105, 5, 395, true);
		portal4.addDoor(395, 105, 5, 395, true);
		portal2.addDoor(415, 105, 5, 395, false);

		levelName.setText("The Web");
		levelName.setX(263);

		setBall(150, 150);
		setDest(308, 300);

		solutionText
				.setText("Solution: Orange -> \nBlue -> Pink -> Grey -> \nPurple -> Blue -> Brown -> \nBlue -> Brown");

	}

	/**
	 * Map setup of level 7 Sol: P-->O-->C-->O-->P-->O-->B
	 */
	private void level7() {

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		myWall.add(new Rectangle(200, 100, 20, 300));
		myWall.add(new Rectangle(400, 200, 20, 300));
		myWall.add(new Rectangle(200, 400, 100, 20));
		myWall.add(new Rectangle(300, 200, 100, 20));

		Portal portal1 = new Portal(125, 100, 395, 265, 0, 1, Color.ORANGE, 150, 125, 380, 290);
		portal1.addDoor(105, 300, 95, 5, true);
		portal1.addDoor(220, 200, 80, 5, false);
		portal1.addDoor(420, 400, 80, 5, true);
		myPortal.add(portal1);
		Portal portal2 = new Portal(100, 175, 495, 265, 1, 1, Color.BLUE, 125, 200, 480, 290);
		portal2.addDoor(205, 420, 5, 80, true);
		portal2.addDoor(405, 105, 5, 95, true);
		myPortal.add(portal2);
		Portal portal3 = new Portal(195, 175, 275, 100, 1, 0, Color.PINK, 180, 200, 300, 125);
		portal3.addDoor(420, 210, 80, 5, true);
		portal3.addDoor(300, 405, 100, 5, true);
		myPortal.add(portal3);
		Portal portal4 = new Portal(125, 495, 215, 325, 0, 1, Color.CYAN, 150, 480, 240, 350);
		portal4.addDoor(105, 410, 95, 5, false);
		portal4.addDoor(420, 360, 80, 5, true);
		myPortal.add(portal4);

		levelName.setText("The Swirl");
		levelName.setX(258);

		setBall(150, 150);
		setDest(450, 450);

		solutionText.setText("Solution: Pink -> \nOrange -> Cyan -> \nOrange -> Pink -> \nOrange -> Blue");
	}

	/**
	 * Map setup of level 8 Sol: O-->P-->C-->OR-->R-->C-->R-->O-->P-->C
	 */
	private void level8() {
		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		myWall.add(new Rectangle(100, 200, 300, 5));
		myWall.add(new Rectangle(200, 200, 5, 100));
		myWall.add(new Rectangle(200, 400, 5, 100));
		myWall.add(new Rectangle(400, 200, 5, 100));
		myWall.add(new Rectangle(400, 400, 5, 100));

		Portal portal1 = new Portal(205, 325, 495, 325, 1, 1, Color.ORANGE, 235, 350, 480, 350);
		Portal portal2 = new Portal(390, 325, 425, 100, 1, 0, Color.RED, 375, 350, 450, 125);
		Portal portal3 = new Portal(100, 325, 495, 225, 1, 1, Color.PINK, 125, 350, 480, 250);
		Portal portal4 = new Portal(100, 425, 495, 125, 1, 1, Color.CYAN, 125, 450, 480, 150);
		Door d1 = new Door(400, 300, 5, 100, Color.ORANGE);
		portal1.addDoor(d1, true);
		d1.connectPortal(portal2);
		Door d2 = new Door(200, 300, 5, 100, Color.RED);
		portal2.addDoor(d2, true);
		d2.connectPortal(portal1);
		portal1.addDoor(250, 105, 5, 95, true);
		portal1.addDoor(410, 300, 5, 95, true);
		portal1.addDoor(405, 340, 5, 20, true);
		portal2.addDoor(300, 105, 5, 95, false);
		portal2.addDoor(190, 300, 5, 95, true);
		portal2.addDoor(195, 340, 5, 20, true);
		portal3.addDoor(405, 200, 95, 5, true);
		portal3.addDoor(400, 105, 5, 95, false);
		portal4.addDoor(105, 400, 95, 5, false);
		portal4.addDoor(200, 105, 5, 95, true);
		myPortal.add(portal1);
		myPortal.add(portal2);
		myPortal.add(portal3);
		myPortal.add(portal4);

		setBall(300, 350);
		setDest(150, 150);

		Text t1 = new Text("CAUTION: When a door opens...");
		t1.setX(205);
		t1.setY(260);
		t1.setScaleX(0.9);
		t1.setScaleY(0.9);
		t1.setFill(Color.GREEN);
		root.getChildren().add(t1);
		Text t2 = new Text("...the portal on it disappears too");
		t2.setX(205);
		t2.setY(460);
		t2.setScaleX(0.9);
		t2.setScaleY(0.9);
		t2.setFill(Color.GREEN);
		root.getChildren().add(t2);

		levelName.setText("The Connection");
		levelName.setX(225);

		solutionText.setText(
				"Solution: Orange -> \nPink -> Cyan -> Right Orange \n-> Red -> Cyan -> Red -> \nOrange -> Pink -> Cyan");
	}

	/**
	 * Set the location of the ball (player)
	 *
	 * @param x-axis
	 *            of the player (the ball)
	 * @param y-axis
	 *            of the player (the ball)
	 */
	private void setBall(int ballX, int ballY) {
		myBall = new Circle();
		myBall.setCenterX(ballX);
		myBall.setCenterY(ballY);
		myBall.setRadius(RADIUS);
		myBall.setFill(ballColor);
		ballInitX = ballX;
		ballInitY = ballY;
		root.getChildren().add(myBall);
	}

	/**
	 * Set the location of the destination (target)
	 *
	 * @param x-axis
	 *            of the target
	 * @param x-axis
	 *            of the target
	 */
	private void setDest(int destX, int destY) {
		myDest = new Circle();
		myDest.setCenterX(destX);
		myDest.setCenterY(destY);
		myDest.setRadius(RADIUS);
		myDest.setFill(destColor);
		root.getChildren().add(myDest);
	}

	/**
	 * @return whether the ball has touched the black wall
	 */
	private boolean touchedWall() {
		if (cheat == true)
			return false;
		for (Rectangle r : myWall) {
			Shape intersect = Shape.intersect(myBall, r);
			if (intersect.getBoundsInLocal().getWidth() != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return whether the ball has touched any colored door (stop)
	 */
	private boolean touchedDoor() {
		if (cheat == true)
			return false;
		for (Portal p : myPortal) {
			for (Door d : p.getAllDoors()) {
				Shape intersect = Shape.intersect(myBall, d.getDoor());
				if (intersect.getBoundsInLocal().getWidth() != -1 && d.getVisibility()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return whether the ball has touched any portal to transport, it also has
	 *         to touch the wall (go into the portal)
	 */
	private boolean touchedPortal() {
		if (cheat == true)
			return false;
		for (Portal p : myPortal) {
			Shape intersect1 = Shape.intersect(myBall, p.geth1().getRec());
			if (intersect1.getBoundsInLocal().getWidth() != -1) {
				myBall.setCenterX(p.getExit2X());
				myBall.setCenterY(p.getExit2Y());
				p.toggleVisibility();
				return true;
			}
			Shape intersect2 = Shape.intersect(myBall, p.geth2().getRec());
			if (intersect2.getBoundsInLocal().getWidth() != -1) {
				myBall.setCenterX(p.getExit1X());
				myBall.setCenterY(p.getExit1Y());
				p.toggleVisibility();
				return true;
			}
		}
		return false;
	}

	/**
	 * @return whether the player passes the level (reach the destination)
	 */
	private boolean touchedDest() {
		Shape intersect = Shape.intersect(myBall, myDest);
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			return true;
		}
		return false;
	}

	private void handleKeyInput(KeyCode code) {
		switch (code) {
		case I:
			cheatText.setVisible(!cheatText.isVisible());
			cheat = !cheat;
			break;
		case P:
			buttonLevel.setVisible(!buttonLevel.isVisible());
			break;
		case R:
			reset();
			break;
		case H:
			showHint();
			break;
		case RIGHT:
			myBall.setCenterX(myBall.getCenterX() + KEY_INPUT_SPEED);
			if (touchedWall() || touchedDoor()) {
				if (!touchedPortal()) {
					myBall.setCenterX(myBall.getCenterX() - KEY_INPUT_SPEED);
				}
			}
			if (touchedDest()) {
				myBall.setCenterX(myBall.getCenterX() - KEY_INPUT_SPEED);
				buttonLevel.setVisible(true);
				showWinMessage();
			}
			break;
		case LEFT:
			myBall.setCenterX(myBall.getCenterX() - KEY_INPUT_SPEED);
			if (touchedWall() || touchedDoor()) {
				if (!touchedPortal()) {
					myBall.setCenterX(myBall.getCenterX() + KEY_INPUT_SPEED);
				}
			}
			if (touchedDest()) {
				myBall.setCenterX(myBall.getCenterX() + KEY_INPUT_SPEED);
				buttonLevel.setVisible(true);
				showWinMessage();
			}
			break;
		case UP:
			myBall.setCenterY(myBall.getCenterY() - KEY_INPUT_SPEED);
			if (touchedWall() || touchedDoor()) {
				if (!touchedPortal()) {
					myBall.setCenterY(myBall.getCenterY() + KEY_INPUT_SPEED);
				}
			}
			if (touchedDest()) {
				myBall.setCenterY(myBall.getCenterY() + KEY_INPUT_SPEED);
				buttonLevel.setVisible(true);
				showWinMessage();
			}
			break;
		case DOWN:
			myBall.setCenterY(myBall.getCenterY() + KEY_INPUT_SPEED);
			if (touchedWall() || touchedDoor()) {
				if (!touchedPortal()) {
					myBall.setCenterY(myBall.getCenterY() - KEY_INPUT_SPEED);
				}
			}
			if (touchedDest()) {
				myBall.setCenterY(myBall.getCenterY() - KEY_INPUT_SPEED);
				buttonLevel.setVisible(true);
				showWinMessage();
			}
			break;
		default:
		}
	}

	/**
	 * Reset the board to its initial state
	 */
	private void reset() {
		myBall.setVisible(false);
		setBall(ballInitX, ballInitY);
		winText.setVisible(false);

		for (Portal p : myPortal) {
			if (p.getCount() == 1) {
				p.toggleVisibility();
				p.geth1().getRec().setVisible(true);
				p.geth2().getRec().setVisible(true);
			}
		}
	}

	/**
	 * show the "Congratulation!" message
	 */
	private void showWinMessage() {
		winText.setVisible(true);
	}

	/**
	 * show the solution
	 */
	private void showHint() {
		this.solutionText.setVisible(!this.solutionText.isVisible());
	}

	public void step(double secondDelay) {

	}
}
