package ru.itis.ruzavin.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import ru.itis.ruzavin.entity.Player;
import ru.itis.ruzavin.entity.ai.Bot;
import ru.itis.ruzavin.map.GameMap;
import ru.itis.ruzavin.map.entity.Border;
import ru.itis.ruzavin.map.entity.Checkpoint;
import ru.itis.ruzavin.map.entity.Finish;
import ru.itis.ruzavin.map.entity.MapObject;
import ru.itis.ruzavin.menu.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class GameLoop {

	private Pane root;
	private final List<MapObject> mapObjects = new ArrayList<>();
	private Player player;
	private Bot bot;
	private final Font font = Font.font("Courier New", FontWeight.BOLD, 20);
	private final GameMap gameMap = new GameMap();
	private Stage stage;

	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(createContent()));
		stage.getScene().setOnKeyPressed(k -> player.moveForward(k.getCode()));
		stage.show();
	}

	private Parent createContent() {
		root = new Pane();
		root.setPrefSize(1000, 1000);

		Text botNick = new Text(300, 920, "bot");
		Text nick = new Text(250, 920, MainMenu.getNick());
		nick.setFont(font);
		botNick.setFont(font);

		bot = new Bot(300, 930, botNick);
		Thread botThread = new Thread(bot);
		botThread.start();

		player = new Player(250, 930, nick);

		gameMap.createMap(mapObjects);

		mapObjects.forEach((object -> root.getChildren().add(object.getView())));
		root.getChildren().add(player.getView());
		root.getChildren().add(nick);
		root.getChildren().add(botNick);
		root.getChildren().add(bot.getView());

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				onUpdate();
				onUpdateBot();
			}
		};

		timer.start();

		return root;
	}

	private void onUpdateBot() {
		bot.moveBot(mapObjects);
	}

	@SneakyThrows
	private void onUpdate() {
		player.moveForward(player.isDriving());
		for (MapObject object : mapObjects) {
			if (player.isCollideWithMap(object)) {
				Point2D lastCheckpoint = player.getLastCheckpoint();
				if (object instanceof Checkpoint) {
					player.setLastCheckpoint(new Point2D(object.getPosition().getX(), object.getPosition().getY()));
					break;
				}
				if (object instanceof Finish) {
					Platform.exit();
				}
				player.moveToCheckpoint(lastCheckpoint.getX(), lastCheckpoint.getY(), player.getRotate());
			}
		}
	}

}
