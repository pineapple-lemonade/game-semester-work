package ru.itis.ruzavin.net.client;

import lombok.Data;
import ru.itis.ruzavin.game.GameLoop;

import java.io.*;

@Data
public class ClientThread implements Runnable {

	private final BufferedReader inputStream;
	private final BufferedWriter outputStream;
	private final Client client;
	private final GameLoop gameLoop = GameLoop.getInstance();
	private boolean isWorking = true;

	public ClientThread(BufferedReader input, BufferedWriter output, Client client) {
		this.inputStream = input;
		this.outputStream = output;
		this.client = client;
	}

	public void stop() {
		isWorking = false;
	}

	@Override
	public void run() {
		try {
			while (isWorking) {
				String message = inputStream.readLine();

				if (message != null) {
					String[] messageSplit = message.split(",");

					switch (messageSplit[0]) {
						case "connected":
							gameLoop.createAnotherPlayer(messageSplit[1]);
							break;
						case "move":
							String nick = messageSplit[1];
							double x = Double.parseDouble(messageSplit[2]);
							double y = Double.parseDouble(messageSplit[3]);
							boolean isDriving = Boolean.parseBoolean(messageSplit[4]);
							double rotation = Double.parseDouble(messageSplit[5]);
							gameLoop.moveAnotherPlayer(nick, rotation, isDriving);
							break;
					}

//					if (messageSplit[0].equals("win")) {
//						map.showWinMenu(messageSplit[1]);
//					}
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
