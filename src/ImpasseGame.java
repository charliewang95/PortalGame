import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Game class
 * 
 * @author Charlie Wang
 * 
 */
public class ImpasseGame {
	public static final String TITLE = "Impasse";
	public static final int KEY_INPUT_SPEED = 5;
	public static final int RADIUS = 15;
	public static final Color ballColor = Color.GREEN;
	public static final Color destColor = Color.RED;
	public int myLevel;
	public String myTitle;
	private Scene myScene;
	private ArrayList<Rectangle> myWall = new ArrayList<Rectangle>();
	private ArrayList<Portal> myPortal = new ArrayList<Portal>();
	private Circle myBall, myDest;

	public String getTitle() {
		return TITLE;
	}

	public Scene init(int width, int height, int level) {
		myLevel = level;
		myWall.clear();
		myPortal.clear();
		Group root = new Group();
		myScene = new Scene(root, width, height, Color.WHITE);

		// Write out the level number
		Text t = new Text("Level " + level);
		t.setX(260);
		t.setY(50);
		root.getChildren().add(t);
		t.setFont(Font.font("Verdana", 30));
		t.setFill(Color.BLACK);
		
		// Write out the author 
		Text t2 = new Text("Created by Charlie Wang");
		t2.setX(420);
		t2.setY(560);
		t.setFont(Font.font("Verdana", 20));
		root.getChildren().add(t2);
		
		// Set up the board according to which level I'm in
		switch (level) {
		case 1:
			level1(root);
			break;
		case 2:
			level2(root);
			break;
		case 3:
			level3(root);
			break;
		default:
			level1(root);
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

	public void level1(Group root) {

		myWall.add(new Rectangle(150, 150, 5, 300));
		myWall.add(new Rectangle(150, 450, 300, 5));
		myWall.add(new Rectangle(150, 150, 100, 5));
		myWall.add(new Rectangle(250, 150, 5, 200));
		myWall.add(new Rectangle(250, 350, 200, 5));
		myWall.add(new Rectangle(450, 350, 5, 105));
		myWall.add(new Rectangle(250, 350, 5, 105));

		setBall(root, 300, 400, RADIUS);
		setDest(root, 200, 400, RADIUS);

		Portal portal1 = new Portal(175, 150, 445, 375, 0, 1, Color.ORANGE, 200, 175, 425, 400);
		portal1.addDoor(155, 350, 95, 5, true);
		portal1.addDoor(350, 355, 5, 95, false);
		myPortal.add(portal1);
	}

	public void level2(Group root) {
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

	public void level3(Group root) {
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
		portal2.addDoor(320, 330, 5, 170, false);
		myPortal.add(portal2);
		Portal portal3 = new Portal(100, 425, 495, 425, 1, 1, Color.BROWN, 125, 450, 480, 450);
		portal3.addDoor(275, 330, 5, 170, true);
		portal3.addDoor(325, 325, 175, 5, false);
		portal3.addDoor(325, 280, 5, 45, false);
		myPortal.add(portal3);
	}

	public void setBall(Group root, int ballX, int ballY, int rad) {
		myBall = new Circle();
		myBall.setCenterX(ballX);
		myBall.setCenterY(ballY);
		myBall.setRadius(rad);
		myBall.setFill(ballColor);
		root.getChildren().add(myBall);
	}

	public void setDest(Group root, int destX, int destY, int rad) {
		myDest = new Circle();
		myDest.setCenterX(destX);
		myDest.setCenterY(destY);
		myDest.setRadius(rad);
		myDest.setFill(destColor);
		root.getChildren().add(myDest);
	}

	public boolean touchedWall() {
		for (Rectangle r : myWall) {
			Shape intersect = Shape.intersect(myBall, r);
			if (intersect.getBoundsInLocal().getWidth() != -1) {
				return true;
			}
		}
		return false;
	}

	public boolean touchedDoor() {
		for (Portal p : myPortal) {
			for (Portal.Door d : p.allMyDoor) {
				Shape intersect = Shape.intersect(myBall, d.myDoor);
				if (intersect.getBoundsInLocal().getWidth() != -1 && d.isVisible) {
					System.out.println(d.isVisible);
					return true;
				}
			}
		}
		return false;
	}

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
			break;
		case LEFT:
			myBall.setCenterX(myBall.getCenterX() - KEY_INPUT_SPEED);
			if (touchedWall() || touchedDoor()) {
				if (!touchedPortal()) {
					myBall.setCenterX(myBall.getCenterX() + KEY_INPUT_SPEED);
				}
			}
			break;
		case UP:
			myBall.setCenterY(myBall.getCenterY() - KEY_INPUT_SPEED);
			if (touchedWall() || touchedDoor()) {
				if (!touchedPortal()) {
					myBall.setCenterY(myBall.getCenterY() + KEY_INPUT_SPEED);
				}
			}
			break;
		case DOWN:
			myBall.setCenterY(myBall.getCenterY() + KEY_INPUT_SPEED);
			if (touchedWall() || touchedDoor()) {
				if (!touchedPortal()) {
					myBall.setCenterY(myBall.getCenterY() - KEY_INPUT_SPEED);
				}
			}
			break;
		default:
		}
	}

	public Object step(double secondDelay) {

		return null;
	}

}
