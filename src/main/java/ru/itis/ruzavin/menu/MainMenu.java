package ru.itis.ruzavin.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lombok.Data;
import lombok.SneakyThrows;
import ru.itis.ruzavin.game.GameLoop;

@Data
public class MainMenu {
	private Pane pane;
	private TextField nickTextField;
	private Button singlePlayerButton;
	private Button multiPlayerButton;
	private Font font;
	private Stage stage;
	private static String nick;

	public MainMenu() {
		createContent();
	}

	private final EventHandler<ActionEvent> singlePlayerEvent = new EventHandler<ActionEvent>() {
		@SneakyThrows
		@Override
		public void handle(ActionEvent event) {
			if (singlePlayerButton == event.getSource()){
				nick = nickTextField.getText();
				GameLoop gameLoop = new GameLoop();
				gameLoop.start(stage);
			}
		}
	};

	private final EventHandler<ActionEvent> multiPlayerEvent = new EventHandler<ActionEvent>() {
		@SneakyThrows
		@Override
		public void handle(ActionEvent event) {
			if (singlePlayerButton == event.getSource()){
				nick = nickTextField.getText();
				GameLoop gameLoop = new GameLoop();
				gameLoop.start(stage);
			}
		}
	};


	private void createContent(){
		pane = new AnchorPane();
		pane.setPrefSize(800, 800);

		font = Font.font("Calibri Light", FontWeight.MEDIUM, 18);

		singlePlayerButton = new Button("Play single");
		singlePlayerButton.setMaxSize(400, 400);
		singlePlayerButton.setFont(font);
		singlePlayerButton.setTranslateX(350);
		singlePlayerButton.setTranslateY(300);
		singlePlayerButton.setOnAction(singlePlayerEvent);

		multiPlayerButton = new Button("Play multi");
		multiPlayerButton.setMaxSize(400, 400);
		multiPlayerButton.setFont(font);
		multiPlayerButton.setTranslateX(350);
		multiPlayerButton.setTranslateY(400);
		multiPlayerButton.setOnAction(multiPlayerEvent);

		nickTextField = new TextField();
		nickTextField.setMaxSize(200, 200);
		nickTextField.setTranslateX(310);
		nickTextField.setTranslateY(500);

		pane.getChildren().add(singlePlayerButton);
		pane.getChildren().add(multiPlayerButton);
		pane.getChildren().add(nickTextField);

	}

	public void show(){
		Scene scene = new Scene(pane);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	public static String getNick(){
		return nick;
	}
}
