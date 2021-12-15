package ru.itis.ruzavin.entity.ai;

import javafx.scene.text.Text;
import lombok.SneakyThrows;
import ru.itis.ruzavin.entity.Player;

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
}
