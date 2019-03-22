package me.evanschenkman.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static int scale = 3;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		
		frame = new JFrame();
	}
	
	public synchronized void start() {
		thread = new Thread(this, "Display");
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			update();
			render();
		}
	}
	
	private void update() {
		
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, width, height);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) { 
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("600 Seconds");
		game.frame.add(game);
		game.frame.setUndecorated(true);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}
