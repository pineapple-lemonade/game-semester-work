package ru.itis.ruzavin.entity;

import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import ru.itis.ruzavin.game.GameLoop;
import ru.itis.ruzavin.map.GameMap;

@Getter
@Setter
public class AnotherPlayer extends Player{
	private final GameMap map = new GameMap();
	private String name;
	private GameLoop gameLoop;

	public AnotherPlayer(Text nick, String name) {
		super(300, 930, nick, false);
		this.name = name;
	}


	public synchronized void move(double rotation) {
		moveAndSendMessage(rotation);
		this.getView().setRotate(rotation);
	}

	public synchronized void tpPlayer(double x, double y, double rotation){
		moveToCheckpoint(x, y, rotation);
	}

	public synchronized void stopRendering() {
		javafx.application.Platform.runLater(() -> {
			getNick().setText("");
			GameLoop.getRoot().getChildren().remove(getNick());
			GameLoop.getRoot().getChildren().remove(imageView);
		});
	}


}

