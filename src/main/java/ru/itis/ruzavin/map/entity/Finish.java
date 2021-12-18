package ru.itis.ruzavin.map.entity;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Finish extends MapObject{
	public Finish(double x, double y, double width, double height) {
		super(new Rectangle(x, y, width, height), new Point2D(x, y));
	}
}
