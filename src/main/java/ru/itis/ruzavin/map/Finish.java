package ru.itis.ruzavin.map;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Finish extends MapObject{
	public Finish(double x, double y) {
		super(new Rectangle(x, y, 25, 25), new Point2D(x, y));
	}
}
