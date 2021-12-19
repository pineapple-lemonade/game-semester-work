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
	private final Text nick;
	private String name;
	private GameLoop gameLoop;

	public AnotherPlayer(Text nick, String name) {
		super(300, 930, nick, true);
		this.nick = nick;
		this.name = name;
	}


	public synchronized void move(double rotation) {
		moveAndSendMessage(rotation);
	}

	public synchronized void stopRendering() {
		javafx.application.Platform.runLater(() -> {
			nick.setText("");
			GameLoop.getRoot().getChildren().remove(nick);
			GameLoop.getRoot().getChildren().remove(imageView);
		});
	}
}

