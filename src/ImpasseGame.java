import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Game class
 * 
 * @author Charlie Wang
 * 
 */
public class ImpasseGame {
	public static final String TITLE = "Impasse";
	public static final int KEY_INPUT_SPEED = 10;
	public static final boolean CANGOTONEXTLEVEL = true;
	public static final int RADIUS = 15;
	public static final Color ballColor = Color.GREEN;
	public static final Color destColor = Color.RED;
	public int myLevel;
	public boolean passed = false;
	public String myTitle;
	private Scene myScene;
	private int myWidth, myHeight;
	private ArrayList<Rectangle> myWall = new ArrayList<Rectangle>();
	private ArrayList<Portal> myPortal = new ArrayList<Portal>();
	private Circle myBall, myDest;
	private Group root;

	public Button buttonLevel, buttonBegin, buttonReset;
	private Text t3;

	public boolean hasPassed() {
		return passed;
	}

	/*
	 * returns the title of the game
	 */
	public String getTitle() {
		return TITLE;
	}

	/*
	 * Initialize the board, draws the map, walls, portals, doors, and balls
	 */
	public Scene init(int width, int height, int level) {
		myWidth = width;
		myHeight = height;
		myLevel = level;
		myWall.clear();
		myPortal.clear();
		root = new Group();
		myScene = new Scene(root, width, height, Color.WHITE);
		myScene.setFill(Color.LIGHTGRAY);
		// Write out the level number
		if (level != 0) {
			Text t = new Text("Level " + level);
			t.setX(270);
			t.setY(60);
			root.getChildren().add(t);
			t.setFont(Font.font("Verdana", 20));
			t.setFill(Color.BLACK);
		}

		// Write out the author
		Text t2 = new Text("Created by Charlie Wang");
		t2.setX(410);
		t2.setY(560);
		t2.setFont(Font.font("Verdana", 12));
		root.getChildren().add(t2);

		// Set up the board according to which level I'm in
		switch (level) {
		case 0:
			level0();
			break;
		case 1:
			levelprep1();
			break;
		case 2:
			level2();
			break;
		case 3:
			level3();
			break;
		case 4:
			level4();
			break;
		case 5:
			level5();
			break;
		default:
			level1();
		}

		// Show all the walls
		for (Rectangle r : myWall) {
			r.setFill(Color.BLACK);
			root.getChildren().add(r);
		}

		// Show all the portals and doors
		for (Portal p : myPortal) {
			root.getChildren().add(p.h1.rec);
			root.getChildren().add(p.h2.rec);
			for (Portal.Door d : p.allMyDoor) {
				root.getChildren().add(d.myDoor);
			}
		}

		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return myScene;
	}

	public void drawNextButton() {
		buttonLevel = new Button("Next Level");
		buttonLevel.setLayoutX(450);
		buttonLevel.setLayoutY(40);
		root.getChildren().add(buttonLevel);
		buttonLevel.setVisible(CANGOTONEXTLEVEL);
	}

	public void drawResetButton() {
		buttonReset = new Button("Reset Level");
		buttonReset.setLayoutX(70);
		buttonReset.setLayoutY(40);
		root.getChildren().add(buttonReset);
		buttonReset.setVisible(true);
	}

	public void level0() {
		DropShadow ds = new DropShadow();
		ds.setOffsetY(8.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		Text t = new Text();
		t.setEffect(ds);
		t.setX(90.0f);
		t.setY(170.0f);
		t.setFill(Color.RED);
		t.setText("IMPASSE");
		t.setFont(Font.font(null, FontWeight.BOLD, 90));
		root.getChildren().add(t);

		buttonBegin = new Button("PLAY!!");
		buttonBegin.setLayoutX(270);
		buttonBegin.setLayoutY(440);
		buttonBegin.setScaleX(3);
		buttonBegin.setScaleY(3);
		root.getChildren().add(buttonBegin);

	}

	public void levelprep1() {
		drawNextButton();
		drawResetButton();
		
		myWall.add(new Rectangle(150, 250, 300, 5));
		myWall.add(new Rectangle(150, 350, 300, 5));
		myWall.add(new Rectangle(150, 250, 5, 100));
		myWall.add(new Rectangle(450, 250, 5, 105));
		myWall.add(new Rectangle(275, 250, 5, 100));
		myWall.add(new Rectangle(325, 250, 5, 105));
		
		setBall(root, 175, 300, RADIUS);
		setDest(root, 425, 300, RADIUS);
		Portal portal1 = new Portal(270, 280, 325, 280, 1, 1, Color.ORANGE, 255, 300, 350, 300);
		myPortal.add(portal1);
		
		Text t1= new Text("A portal wil transport you to another place...");
		t1.setX(160);
		t1.setY(180);
		t1.setScaleX(1.5);
		t1.setScaleY(1.5);
		t1.setFill(Color.GREEN);
		root.getChildren().add(t1);
	}

	public void levelprep2() {
		
	}
	
	/*
	 * Map setup of level 1 Sol: O
	 */
	public void level1() {
		drawNextButton();
		drawResetButton();

		myWall.add(new Rectangle(150, 150, 5, 300));
		myWall.add(new Rectangle(150, 450, 300, 5));
		myWall.add(new Rectangle(150, 150, 100, 5));
		myWall.add(new Rectangle(250, 150, 5, 200));
		myWall.add(new Rectangle(250, 350, 200, 5));
		myWall.add(new Rectangle(450, 350, 5, 105));
		myWall.add(new Rectangle(250, 350, 5, 105));

		setBall(root, 300, 400, RADIUS);
		setDest(root, 200, 400, RADIUS);

		Portal portal1 = new Portal(175, 150, 445, 375, 0, 1, Color.ORANGE, 200, 175, 430, 400);
		portal1.addDoor(155, 350, 95, 5, true);
		portal1.addDoor(350, 355, 5, 95, false);
		myPortal.add(portal1);
	}

	/*
	 * Map setup of level 2 Sol: B-->OL-->B
	 */
	public void level2() {
		drawNextButton();
		drawResetButton();

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		myWall.add(new Rectangle(200, 200, 5, 300));
		myWall.add(new Rectangle(300, 200, 200, 5));
		setBall(root, 450, 450, RADIUS);
		setDest(root, 150, 450, RADIUS);

		Portal portal1 = new Portal(100, 125, 495, 125, 1, 1, Color.ORANGE, 125, 150, 480, 150);
		portal1.addDoor(105, 320, 95, 5, true);
		portal1.addDoor(200, 105, 5, 95, false);
		myPortal.add(portal1);
		Portal portal2 = new Portal(100, 225, 495, 325, 1, 1, Color.BLUE, 125, 250, 480, 350);
		portal2.addDoor(105, 370, 95, 5, false);
		portal2.addDoor(300, 105, 5, 95, true);
		myPortal.add(portal2);
	}

	/*
	 * Map setup of level 3 Sol: O-->OR-->B-->BU-->P-->PL
	 */
	public void level3() {
		drawNextButton();
		drawResetButton();

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		myWall.add(new Rectangle(275, 275, 5, 50));
		myWall.add(new Rectangle(275, 325, 50, 5));
		myWall.add(new Rectangle(325, 275, 175, 5));

		setBall(root, 400, 175, RADIUS);
		setDest(root, 303, 300, RADIUS);

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
	}

	/*
	 * Map setup of level 4 Sol: P-->O-->C-->O-->P-->O-->B
	 */
	public void level4() {
		drawNextButton();
		drawResetButton();

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		myWall.add(new Rectangle(200, 100, 20, 300));
		myWall.add(new Rectangle(400, 200, 20, 300));
		myWall.add(new Rectangle(200, 400, 100, 20));
		myWall.add(new Rectangle(300, 200, 100, 20));
		setBall(root, 150, 150, RADIUS);
		setDest(root, 450, 450, RADIUS);

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
	}

	/*
	 * Map setup of level 5 Sol: O-->BL-->PI-->G-->PU-->BL-->BR-->BL-->BR
	 */
	public void level5() {
		drawNextButton();
		drawResetButton();

		myWall.add(new Rectangle(100, 100, 5, 400));
		myWall.add(new Rectangle(100, 500, 405, 5));
		myWall.add(new Rectangle(100, 100, 400, 5));
		myWall.add(new Rectangle(500, 100, 5, 400));
		setBall(root, 150, 150, RADIUS);
		setDest(root, 308, 300, RADIUS);

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
		portal4.addDoor(105, 250, 395, 5, true);

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

	}

	/*
	 * Set the location of the ball (player)
	 */
	public void setBall(Group root, int ballX, int ballY, int rad) {
		myBall = new Circle();
		myBall.setCenterX(ballX);
		myBall.setCenterY(ballY);
		myBall.setRadius(rad);
		myBall.setFill(ballColor);
		root.getChildren().add(myBall);
	}

	/*
	 * Set the location of the destination (target)
	 */
	public void setDest(Group root, int destX, int destY, int rad) {
		myDest = new Circle();
		myDest.setCenterX(destX);
		myDest.setCenterY(destY);
		myDest.setRadius(rad);
		myDest.setFill(destColor);
		root.getChildren().add(myDest);
	}

	/*
	 * Detect whether the ball has touched the black wall
	 */
	public boolean touchedWall() {
		for (Rectangle r : myWall) {
			Shape intersect = Shape.intersect(myBall, r);
			if (intersect.getBoundsInLocal().getWidth() != -1) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Detect whether the ball has touched any colored door (stop)
	 */
	public boolean touchedDoor() {
		for (Portal p : myPortal) {
			for (Portal.Door d : p.allMyDoor) {
				Shape intersect = Shape.intersect(myBall, d.myDoor);
				if (intersect.getBoundsInLocal().getWidth() != -1 && d.isVisible) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Detect whether the ball has touched any portal to transport, it also has
	 * to touch the wall (go into the portal)
	 */
	public boolean touchedPortal() {
		for (Portal p : myPortal) {
			Shape intersect1 = Shape.intersect(myBall, p.h1.rec);
			if (intersect1.getBoundsInLocal().getWidth() != -1) {
				myBall.setCenterX(p.myExit2X);
				myBall.setCenterY(p.myExit2Y);
				p.toggleVisibility();
				return true;
			}
			Shape intersect2 = Shape.intersect(myBall, p.h2.rec);
			if (intersect2.getBoundsInLocal().getWidth() != -1) {
				myBall.setCenterX(p.myExit1X);
				myBall.setCenterY(p.myExit1Y);
				p.toggleVisibility();
				return true;
			}
		}
		return false;
	}

	/*
	 * Detect whether the player passes the level (reach the destination)
	 */
	public boolean touchedDest() {
		Shape intersect = Shape.intersect(myBall, myDest);
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			return true;
		}
		return false;
	}

	private void handleKeyInput(KeyCode code) {
		switch (code) {
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
				winMessage();
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
				winMessage();
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
				winMessage();
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
				winMessage();
			}
			break;
		default:
		}
	}

	/*
	 * Print the winning message
	 */
	private void winMessage() {
		// Write Win message
		DropShadow ds = new DropShadow();
		ds.setOffsetY(8.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		t3 = new Text();
		t3.setEffect(ds);
		t3.setX(50.0f);
		t3.setY(270.0f);
		t3.setFill(Color.RED);
		t3.setText("Congratulations!");
		t3.setFont(Font.font(null, FontWeight.BOLD, 60));
		root.getChildren().add(t3);
		t3.setVisible(true);
	}

	public Object step(double secondDelay) {
		return null;
	}
}
