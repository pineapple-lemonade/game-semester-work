package ru.itis.ruzavin.entity;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import ru.itis.ruzavin.map.MapObject;

@Getter
@Setter
public class Player extends GameObject{

	private Point2D lastCheckpoint;

	private boolean isDriving;

	public Player() {
		super(new Rectangle(40,20, Color.GREEN));
		this.lastCheckpoint = new Point2D(300, 300);
		this.getView().setTranslateX(lastCheckpoint.getX());
		this.getView().setTranslateY(lastCheckpoint.getY());
		this.isDriving = false;
	}

	public void moveToCheckpoint(double x, double y, double rotation){
		getView().setTranslateX(x);
		getView().setTranslateY(y);
		getView().setRotate(rotation);
		isDriving = false;
	}

	public boolean isCollideWithMap(MapObject object){
		return getView().getBoundsInParent().intersects(object.getView().getBoundsInParent());
	}
}
