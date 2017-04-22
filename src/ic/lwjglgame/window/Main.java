package ic.lwjglgame.window;

import ic.lwjglgame.game.Game;
import ic.lwjglgame.input.Input;

public class Main {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Game Window";
	
	private static Window window;
	private static Game game;
	
	private static Input input;
	
	public static void main(String[] args) {
		window = new Window(WIDTH, HEIGHT, TITLE);
		
		long windowId = window.init();
		input = new Input(windowId);
		game = new Game(input);

		while(!window.shouldClose()) {
			game.update();
			game.render();
			
			window.run();
		}
		
		game.destroy();
		window.destroy();
	}

}
