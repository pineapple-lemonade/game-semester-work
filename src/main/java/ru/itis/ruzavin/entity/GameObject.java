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


}
