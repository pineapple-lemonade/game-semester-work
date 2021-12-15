package ru.itis.ruzavin.map;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Border extends MapObject{
	public Border(double x, double y, double rotation, double width, double height) {
		super(new Rectangle(x, y, width, height), new Point2D(x, y));
		getView().setRotate(rotation);
	}
}
