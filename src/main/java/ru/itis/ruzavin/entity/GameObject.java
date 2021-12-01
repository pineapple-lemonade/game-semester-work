package ru.itis.ruzavin.entity;

import javafx.scene.Node;
import lombok.Data;

@Data
public class GameObject {

	private Node view;

	public GameObject(Node view) {
		this.view = view;
	}

	public void update() {
		view.setTranslateX(view.getTranslateX());
		view.setTranslateY(view.getTranslateY());
	}

	public double getRotate() {
		return view.getRotate();
	}

	public void rotateRight() {
		view.setRotate(view.getRotate() + 6);
	}

	public void rotateLeft() {
		view.setRotate(view.getRotate() - 6);
	}

	public void moveForward() {
		double rotation = view.getRotate() % 360;

		if ((rotation >= 0 && rotation <= 90) || (rotation <= -270 && rotation >= -360)) {
			view.setTranslateX(view.getTranslateX() - Math.pow(Math.cos(Math.toRadians(getRotate())), 2) * 2);
			view.setTranslateY(view.getTranslateY() - Math.pow(Math.sin(Math.toRadians(getRotate())), 2) * 2);
		}

		if ((rotation >= 90 && rotation <= 180) || (rotation <= -180 && rotation >= -270)) {
			view.setTranslateX(view.getTranslateX() + Math.pow(Math.cos(Math.toRadians(getRotate())), 2) * 2);
			view.setTranslateY(view.getTranslateY() - Math.pow(Math.sin(Math.toRadians(getRotate())), 2) * 2);
		}

		if ((rotation >= 180 && rotation <= 270) || (rotation <= -90 && rotation >= -180)) {
			view.setTranslateX(view.getTranslateX() + Math.pow(Math.cos(Math.toRadians(getRotate())), 2) * 2);
			view.setTranslateY(view.getTranslateY() + Math.pow(Math.sin(Math.toRadians(getRotate())), 2) * 2);
		}

		if ((rotation >= 270 && rotation <= 360) || (rotation <= 0 && rotation >= -90)) {
			view.setTranslateX(view.getTranslateX() - Math.pow(Math.cos(Math.toRadians(getRotate())), 2) * 2);
			view.setTranslateY(view.getTranslateY() + Math.pow(Math.sin(Math.toRadians(getRotate())), 2) * 2);
		}

	}



}
