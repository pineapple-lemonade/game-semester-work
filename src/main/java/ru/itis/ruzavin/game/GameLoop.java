package ru.itis.ruzavin.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import ru.itis.ruzavin.entity.Player;
import ru.itis.ruzavin.entity.ai.Bot;
import ru.itis.ruzavin.map.Border;
import ru.itis.ruzavin.map.Checkpoint;
import ru.itis.ruzavin.map.Finish;
import ru.itis.ruzavin.map.MapObject;
import ru.itis.ruzavin.menu.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class GameLoop {

	private Pane root;
	private List<MapObject> mapObjects = new ArrayList<>();
	private Player player;
	private Bot bot;
	private Font font = Font.font("Courier New", FontWeight.BOLD, 20);
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
		MapObject checkPoint = new Checkpoint(650, 500, 50, 25);
		MapObject border = new Border(310, 950, 90, 5, 200);
		MapObject border1 = new Border(410, 850, 0, 5, 200);
		MapObject border2 = new Border(210, 850, 0, 5, 200);
		MapObject border3 = new Border(235, 825, 90, 5, 50);
		MapObject border4 = new Border(385, 825, 90, 5, 50);
		MapObject border5 = new Border(385, 550, 0, 5, 300);
		MapObject border6 = new Border(235, 500, 0, 5, 350);
		MapObject border7 = new Border(410, 325, 90, 5, 350);
		MapObject border8 = new Border(560, 375, 90, 5, 350);
		MapObject border9 = new Border(585, 0, 0, 5, 500);
		MapObject border10 = new Border(735, 3, 0, 5, 550);
		MapObject border11 = new Border(710, 400, 90, 5, 50);
		MapObject border12 = new Border(630, 330, 90, 5, 90);
		MapObject finish = new Finish(610, 30, 100, 50);

		mapObjects.add(checkPoint);
		mapObjects.add(border);
		mapObjects.add(border1);
		mapObjects.add(border2);
		mapObjects.add(border3);
		mapObjects.add(border4);
		mapObjects.add(border5);
		mapObjects.add(border6);
		mapObjects.add(border7);
		mapObjects.add(border8);
		mapObjects.add(border9);
		mapObjects.add(border10);
		mapObjects.add(border11);
		mapObjects.add(border12);
		mapObjects.add(finish);


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
		bot.moveForward(bot.isDriving());

		if (bot.getView().getTranslateY() == 510) {
			bot.getView().setRotate(180);
		}

		if (bot.getView().getTranslateX() == 600) {
			bot.getView().setRotate(90);
		}

		if (bot.getView().getTranslateY() == 400) {
			bot.getView().setRotate(180);
		}

		if (bot.getView().getTranslateX() == 690) {
			bot.getView().setRotate(90);
		}

		if (bot.isCollideWithMap(mapObjects.get(mapObjects.size() - 1))){
			Platform.exit();
		}
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
