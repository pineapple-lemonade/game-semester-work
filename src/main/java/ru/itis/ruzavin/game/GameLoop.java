package ru.itis.ruzavin.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.SneakyThrows;
import ru.itis.ruzavin.entity.AnotherPlayer;
import ru.itis.ruzavin.entity.Player;
import ru.itis.ruzavin.entity.ai.Bot;
import ru.itis.ruzavin.map.GameMap;
import ru.itis.ruzavin.map.entity.MapObject;
import ru.itis.ruzavin.menu.MainMenu;
import ru.itis.ruzavin.net.client.Client;

import java.io.File;
import java.io.IOException;
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
	private static Client client;
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
		try {
			stage.setScene(new Scene(createMultiPlayerContent()));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
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
				javafx.application.Platform.runLater(() -> root.getChildren().add(anotherPlayer.getImageView()));
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

		Image background = new Image(new File("src/main/resources/img.background/Water_Tile.png").toURI().toString());
		ImageView backgroundView = new ImageView(background);
		backgroundView.setFitHeight(1000);
		backgroundView.setFitWidth(1000);
		root.getChildren().add(backgroundView);

		MAP_OBJECTS.forEach((object -> root.getChildren().add(object.getView())));
		root.getChildren().add(nick);
		root.getChildren().add(botNick);
		root.getChildren().add(bot.getView());
		GameMap.getImageViewList().forEach(imageView -> root.getChildren().add(imageView));
		root.getChildren().add(player.getView());



		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				onUpdate();
				onUpdateBot();
			}
		};

		timer.start();

		javafx.application.Platform.runLater(() -> root.getChildren().add(player.getImageView()));
		javafx.application.Platform.runLater(() -> root.getChildren().add(bot.getImageView()));

		return root;
	}

	private Parent createMultiPlayerContent() throws IOException {
		client = new Client();
		client.start();
		root = new Pane();
		root.setPrefSize(1000, 1000);

		Text nick = new Text(250, 920, MainMenu.getNick());
		nick.setFont(FONT);

		player = new Player(250, 930, nick, true);
		player.setClient(client);

		GAME_MAP.createMap(MAP_OBJECTS);

		MAP_OBJECTS.forEach((object -> root.getChildren().add(object.getView())));
		root.getChildren().add(player.getView());
		root.getChildren().add(nick);
		javafx.application.Platform.runLater(() -> root.getChildren().add(player.getImageView()));
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

	public synchronized void tpAnotherPlayer(double x, double y, double rotation, String name) {
		for (AnotherPlayer anotherPlayer : ANOTHER_PLAYERS) {
			if (anotherPlayer.getName().equals(name)) {
				Platform.runLater(() -> anotherPlayer.setLastCheckpoint(new Point2D(x, y)));
				Platform.runLater(() -> anotherPlayer.tpPlayer(x, y, rotation));
			}
		}
	}
	//public synchronized void
}
