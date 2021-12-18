package ru.itis.ruzavin.map.entity;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import lombok.Getter;

@Getter
public class MapObject {
	private Node view;
	private Point2D position;
	public MapObject(Node node, Point2D position) {
		this.view = node;
		this.position = position;
	}
}
