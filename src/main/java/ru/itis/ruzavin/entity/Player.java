package ru.itis.ruzavin.entity;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import ru.itis.ruzavin.map.entity.MapObject;

@Getter
@Setter
public class Player extends GameObject{

	private Point2D lastCheckpoint;

	private boolean isDriving;

	private Text nick;

	public Player(double x, double y, Text nick) {
		super(new Rectangle(40,20, Color.GREEN));
		this.lastCheckpoint = new Point2D(x, y);
		this.getView().setTranslateX(lastCheckpoint.getX());
		this.getView().setTranslateY(lastCheckpoint.getY());
		this.getView().setRotate(90);
		this.isDriving = false;
		this.nick = nick;
	}

	public void moveToCheckpoint(double x, double y, double rotation){
		getView().setTranslateX(x);
		getView().setTranslateY(y);
		getView().setRotate(rotation);
		nick.setX(x);
		nick.setY(y - 10);
		isDriving = false;
	}

	public boolean isCollideWithMap(MapObject object){
		return getView().getBoundsInParent().intersects(object.getView().getBoundsInParent());
	}

	public void moveForward(KeyCode keyCode) {

		isDriving = true;

		switch (keyCode){
			case W:
				getView().setRotate(90);
				break;
			case A:
				getView().setRotate(0);
				break;
			case D:
				getView().setRotate(180);
				break;
			case S:
				getView().setRotate(270);
				break;
		}

		double rotation = getView().getRotate();

		checkRotationAndMove(rotation);

	}

	public void moveForward(boolean isDriving){
		if (isDriving){
			double rotation = getView().getRotate();
			checkRotationAndMove(rotation);
		}
	}

	private void checkRotationAndMove(double rotation){
		if (rotation == 0){
			getView().setTranslateX(getView().getTranslateX() - 2);
			nick.setX(nick.getX() - 2);
		}

		if (rotation == 90){
			getView().setTranslateY(getView().getTranslateY() - 2);
			nick.setY(nick.getY() - 2);
		}

		if (rotation == 180){
			getView().setTranslateX(getView().getTranslateX() + 2);
			nick.setX(nick.getX() + 2);
		}

		if (rotation == 270) {
			getView().setTranslateY(getView().getTranslateY() + 2);
			nick.setY(nick.getY() + 2);
		}
	}
}
