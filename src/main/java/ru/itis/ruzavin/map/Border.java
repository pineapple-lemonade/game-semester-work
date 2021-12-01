package ru.itis.ruzavin.map;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Border extends MapObject{
	public Border(double x, double y) {
		super(new Rectangle(x, y, 5, 50), new Point2D(x, y));
	}
}
