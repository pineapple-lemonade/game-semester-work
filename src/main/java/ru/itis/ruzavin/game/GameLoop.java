package ru.itis.ruzavin.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import ru.itis.ruzavin.entity.Player;
import ru.itis.ruzavin.map.Border;
import ru.itis.ruzavin.map.Checkpoint;
import ru.itis.ruzavin.map.Finish;
import ru.itis.ruzavin.map.MapObject;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends Application {

	private Pane root;

	private List<MapObject> mapObjects = new ArrayList<>();

	private Player player;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(createContent()));

		stage.getScene().setOnKeyPressed(k -> {
			player.moveForward(k.getCode());
		});
		stage.show();
	}

	private Parent createContent(){
		root = new Pane();
		root.setPrefSize(600, 600);

		player = new Player();
		MapObject checkPoint = new Checkpoint(400, 400);
		MapObject border = new Border(500, 500);
		MapObject finish = new Finish(450, 450);

		mapObjects.add(checkPoint);
		mapObjects.add(border);
		mapObjects.add(finish);

		root.getChildren().add(checkPoint.getView());
		root.getChildren().add(finish.getView());
		root.getChildren().add(border.getView());
		root.getChildren().add(player.getView());

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				onUpdate();
			}
		};
		timer.start();

		return root;
	}

	@SneakyThrows
	private void onUpdate(){
		player.moveForward(player.isDriving());
		for (MapObject object : mapObjects){
			if (player.isCollideWithMap(object)){
				Point2D lastCheckpoint = player.getLastCheckpoint();
				if (object instanceof Checkpoint){
					player.setLastCheckpoint(new Point2D(object.getPosition().getX(), object.getPosition().getY()));
					break;
				}
				if(object instanceof Finish){
					Platform.exit();
				}
				player.moveToCheckpoint(lastCheckpoint.getX(), lastCheckpoint.getY(), player.getRotate());
			}
		}
	}

	public void startGame(){
		launch();
	}
}
