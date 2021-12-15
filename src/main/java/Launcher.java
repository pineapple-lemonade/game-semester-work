import javafx.application.Application;
import javafx.stage.Stage;
import ru.itis.ruzavin.game.GameLoop;
import ru.itis.ruzavin.menu.MainMenu;

public class Launcher extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		MainMenu mainMenu = new MainMenu();
		mainMenu.show();
	}
}
