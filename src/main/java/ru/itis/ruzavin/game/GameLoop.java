package ru.itis.ruzavin.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.SneakyThrows;
import ru.itis.ruzavin.entity.AnotherPlayer;
import ru.itis.ruzavin.entity.Player;
import ru.itis.ruzavin.entity.ai.Bot;
import ru.itis.ruzavin.map.GameMap;
import ru.itis.ruzavin.map.entity.Checkpoint;
import ru.itis.ruzavin.map.entity.Finish;
import ru.itis.ruzavin.map.entity.MapObject;
import ru.itis.ruzavin.menu.MainMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameLoop {

	private static Pane root;
	@Getter
	private final List<MapObject> MAP_OBJECTS = new ArrayList<>();
	private Player player;
	private Bot bot;
	private final Font FONT = Font.font("Courier New", FontWeight.BOLD, 20);
	private final GameMap GAME_MAP = new GameMap();
	private static final GameLoop GAME_LOOP = new GameLoop();
	private static final CopyOnWriteArrayList<AnotherPlayer> ANOTHER_PLAYERS = new CopyOnWriteArrayList<>();
	private Stage stage;

	public static Pane getRoot(){
		return root;
	}

	public static GameLoop getInstance(){
		return GAME_LOOP;
	}

	public void startSinglePlayer(Stage stage){
		stage.setScene(new Scene(createSinglePlayerContent()));
		stage.getScene().setOnKeyPressed(k -> player.moveForward(k.getCode()));
		stage.show();
	}

	public void startMultiPlayer(Stage stage){
		stage.setScene(new Scene(createMultiPlayerContent()));
		stage.getScene().setOnKeyPressed(k -> player.moveForward(k.getCode()));
		stage.show();
		player.getClient().sendMessage("connected," + player.getNick().getText() + "\n");
		System.out.println(ANOTHER_PLAYERS);
	}

	public synchronized void createAnotherPlayer(String name){
		boolean isFound = false;

		for(AnotherPlayer anotherPlayer : ANOTHER_PLAYERS) {
			if (anotherPlayer.getName().equals(name)) {
				isFound = true;
				break;
			}
		}

		if(!isFound) {
			Platform.runLater(() -> {
				Text nick = new Text(300, 920, name);
				nick.setFont(FONT);
				nick.setFill(Color.RED);
				AnotherPlayer anotherPlayer = new AnotherPlayer(nick, name);
				ANOTHER_PLAYERS.add(anotherPlayer);
				root.getChildren().add(anotherPlayer.getView());
				root.getChildren().add(nick);
			});
		}
	}

	private Parent createSinglePlayerContent() {
		root = new Pane();
		root.setPrefSize(1000, 1000);

		Text botNick = new Text(300, 920, "bot");
		Text nick = new Text(250, 920, MainMenu.getNick());
		nick.setFont(FONT);
		botNick.setFont(FONT);

		bot = new Bot(300, 930, botNick);
		Thread botThread = new Thread(bot);
		botThread.start();

		player = new Player(250, 930, nick, false);

		GAME_MAP.createMap(MAP_OBJECTS);

		MAP_OBJECTS.forEach((object -> root.getChildren().add(object.getView())));
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

	private Parent createMultiPlayerContent(){
		root = new Pane();
		root.setPrefSize(1000, 1000);

		Text nick = new Text(250, 920, MainMenu.getNick());
		nick.setFont(FONT);

		player = new Player(250, 930, nick, true);

		GAME_MAP.createMap(MAP_OBJECTS);

		MAP_OBJECTS.forEach((object -> root.getChildren().add(object.getView())));
		root.getChildren().add(player.getView());
		root.getChildren().add(nick);

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				onUpdate();
			}
		};

		timer.start();

		return root;
	}

	private void onUpdateBot() {
		bot.moveBot(MAP_OBJECTS);
	}

	@SneakyThrows
	private void onUpdate() {
		player.checkCollisions();
	}

	public synchronized void moveAnotherPlayer(String name, double rotation, boolean isDriving) {
		boolean isFound = false;

		for (AnotherPlayer anotherPlayer : ANOTHER_PLAYERS) {
			if (anotherPlayer.getName().equals(name)) {
				Platform.runLater(() -> anotherPlayer.move(rotation));
				Platform.runLater(() -> anotherPlayer.setDriving(isDriving));
				isFound = true;
			}
		}

		if (!isFound) {
			createAnotherPlayer(name);
		}
	}
}
