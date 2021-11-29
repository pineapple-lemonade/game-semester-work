package ru.itis.ruzavin.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends GameObject{
	public Player() {
		super(new Rectangle(40,20, Color.GREEN));
	}

	public void moveToCheckpoint(double x, double y){
		getView().setTranslateX(x);
		getView().setTranslateY(y);
		getView().setRotate(0);
	}
}
