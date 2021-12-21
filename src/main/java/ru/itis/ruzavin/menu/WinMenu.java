package ru.itis.ruzavin.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WinMenu {
	private Pane pane = null;
	private VBox vBox;
	public Button closeApp;
	private Stage stage;
	private String winner;
	public WinMenu(Stage stage, String winner) {

		this.stage = stage;
		this.winner = winner;

		configure();
	}

	private final EventHandler<ActionEvent> closeAppEvent = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (closeApp == event.getSource()) {
				stage.close();
				System.exit(1);
			}
		}
	};

	private void configure() {
		javafx.application.Platform.runLater(() -> {
			pane = new AnchorPane();
			vBox = new VBox(15);
			Font FONT = Font.font("Courier New", FontWeight.BOLD, 20);

			closeApp = new Button("Close app");
			closeApp.setOnAction(closeAppEvent);
			closeApp.setMaxWidth(500);
			closeApp.setMaxHeight(500);
			closeApp.setFont(FONT);

			Label winnerLabel = new Label(winner + " is winner!");
			winnerLabel.setFont(FONT);
			winnerLabel.setTextFill(Color.GREEN);

			vBox.getChildren().add(winnerLabel);
			vBox.getChildren().add(closeApp);

			AnchorPane.setTopAnchor(vBox, 5.0);
			AnchorPane.setLeftAnchor(vBox, 10.0);
			AnchorPane.setRightAnchor(vBox, 10.0);
			pane.getChildren().add(vBox);

			Scene scene = new Scene(pane, 300, 100);
			stage.setScene(scene);
			stage.show();
		});
	}
}
