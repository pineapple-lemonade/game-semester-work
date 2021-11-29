package ru.itis.ruzavin.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ru.itis.ruzavin.entity.GameObject;
import ru.itis.ruzavin.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends Application {

	private Pane root;

	private List<GameObject> objects = new ArrayList<>();

	private GameObject player;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(createContent()));

		stage.getScene().setOnKeyPressed(k -> {
			switch (k.getCode()){
				case A:
					player.rotateLeft();
					break;
				case D:
					player.rotateRight();
					break;
				case W:
					player.moveForward();
					break;
			}
		});
		stage.show();
	}

	private Parent createContent(){
		root = new Pane();
		root.setPrefSize(600, 600);

		player = new Player();
		addGameObject(player, 300, 300);

		GameObject rectangle = new GameObject(new Rectangle(500, 305, 5, 500));
		objects.add(rectangle);
		root.getChildren().add(rectangle.getView());

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				onUpdate();
			}
		};
		timer.start();

		return root;
	}

	private void addGameObject(GameObject object, double x, double y){
		object.getView().setTranslateX(x);
		object.getView().setTranslateY(y);
		root.getChildren().add(object.getView());
	}

	private void onUpdate(){
		for (GameObject object : objects){
			if (player.isColliding(object)){
				((Player)player).moveToCheckpoint(300, 300);
			}
		}
	}

	public void startGame(){
		launch();
	}
}
