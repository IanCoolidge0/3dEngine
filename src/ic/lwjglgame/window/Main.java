package ic.lwjglgame.window;

import ic.lwjglgame.game.Game;

public class Main {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Game Window";
	
	private static Window window;
	private static Game game;
	
	public static void main(String[] args) {
		window = new Window(WIDTH, HEIGHT, TITLE);
		
		window.init();
		game = new Game();

		while(!window.shouldClose()) {
			game.update();
			game.render();
			
			window.run();
		}
		
		game.destroy();
		window.destroy();
	}

}
