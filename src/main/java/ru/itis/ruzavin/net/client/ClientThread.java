package ru.itis.ruzavin.net.client;

import lombok.Data;
import ru.itis.ruzavin.map.GameMap;
import java.io.*;

@Data
public class ClientThread implements Runnable {

	private final BufferedReader inputStream;
	private final BufferedWriter outputStream;
	private final Client client;
	private final GameMap map = new GameMap();
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
	}
}
