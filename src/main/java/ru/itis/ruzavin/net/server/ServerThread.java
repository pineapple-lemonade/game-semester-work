package ru.itis.ruzavin.net.server;

import lombok.Data;

import java.io.*;
import java.net.SocketException;

@Data
public class ServerThread implements Runnable{
	private final BufferedReader input;
	private final BufferedWriter output;
	private final Server server;
	private boolean isWorking = true;

	public ServerThread(BufferedReader input, BufferedWriter output, Server server) {
		this.input = input;
		this.output = output;
		this.server = server;
	}

	public void stop() {
		isWorking = false;
	}

	@Override
	public void run() {
		try {
			while (isWorking) {
				String message = input.readLine();
				server.sendMessage(message, this);
			}
		} catch (SocketException socketException) {
			server.removeClient(this);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
