package ru.itis.ruzavin.entity;

import javafx.scene.Node;
import lombok.Data;

@Data
public class GameObject {

	private Node view;

	public GameObject(Node view) {
		this.view = view;
	}



	public double getRotate() {
		return view.getRotate();
	}

}
