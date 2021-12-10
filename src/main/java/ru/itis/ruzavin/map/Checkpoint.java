package ru.itis.ruzavin.map;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;


public class Checkpoint extends MapObject{

	public Checkpoint(double x, double y, double width, double height) {
		super(new Rectangle(x, y , width, height), new Point2D(x , y));
	}

}
