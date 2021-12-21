package ru.itis.ruzavin.map.entity;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;


public class Checkpoint extends MapObject{

	public Checkpoint(double x, double y, double width, double height) {
		super(new Rectangle(x, y , width, height), new Point2D(x , y));
		Image image = new Image(new File("src/main/resources/img.car/Road_01_Tile_07.png").toURI().toString());
		setImageView(new ImageView(image));
		getImageView().setViewport(new Rectangle2D(0, 0, 53, 94));
		getImageView().setY(y);
		getImageView().setX(x);
		((Rectangle) getView()).setFill(Color.TRANSPARENT);
	}

}
