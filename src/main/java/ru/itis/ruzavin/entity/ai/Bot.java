package ru.itis.ruzavin.entity.ai;

import javafx.application.Platform;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import ru.itis.ruzavin.entity.Player;
import ru.itis.ruzavin.map.MapObject;

import java.util.ArrayList;
import java.util.List;

public class Bot extends Player implements Runnable{

	public Bot(double x, double y, Text nick) {
		super(x, y, nick);
		this.getView().setRotate(90);
	}


	@SneakyThrows
	@Override
	public void run() {
		Thread.sleep(2000);
		setDriving(true);
	}

	public void moveBot(List<MapObject> mapObjects){
		moveForward(isDriving());

		if (getView().getTranslateY() == 510) {
			getView().setRotate(180);
		}

		if (getView().getTranslateX() == 600) {
			getView().setRotate(90);
		}

		if (getView().getTranslateY() == 400) {
			getView().setRotate(180);
		}

		if (getView().getTranslateX() == 690) {
			getView().setRotate(90);
		}

		if (isCollideWithMap(mapObjects.get(mapObjects.size() - 1))){
			Platform.exit();
		}
	}
}
