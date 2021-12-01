package ru.itis.ruzavin.map;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;


public class Checkpoint extends MapObject{

	public Checkpoint(double x, double y) {
		super(new Rectangle(x, y , 25, 25), new Point2D(x , y));
	}

}
