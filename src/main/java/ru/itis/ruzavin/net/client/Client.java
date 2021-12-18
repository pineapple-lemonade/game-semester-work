package ru.itis.ruzavin.net.client;

import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Data
public class Client {
	private Socket socket;
	private ClientThread gameThread;

	public void stop() {
		gameThread.stop();
	}

	public boolean sendMessage(String message) {
		boolean isSuccessful;

		try {
			gameThread.getOutputStream().write(message);
			gameThread.getOutputStream().flush();

			isSuccessful = true;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return isSuccessful;
	}

	public void start() throws IOException {
		socket = new Socket("127.0.0.1", 5555);

		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

		gameThread = new ClientThread(input, output, this);

		new Thread(gameThread).start();
	}
}
