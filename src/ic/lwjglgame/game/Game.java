package ic.lwjglgame.game;

import ic.lwjglgame.input.Keyboard;

public class Game extends BaseGame {

	 Model rect;
	
	 float[] vertices = {
             -0.5f, 0.5f, 0f,
             -0.5f, -0.5f, 0f,
             0.5f, -0.5f, 0f,
             0.5f, 0.5f, 0f
     };

     int[] indices = {
             0, 1, 2,
             2, 3, 0
     };
     
     @Override
     public void update() {
    	 super.update();
     }
	
	public Game(Keyboard keyboard) {
		super(keyboard);
		
		rect = ModelLoader.loadVAOFromOBJ("src/models/cube.obj", "src/models/cube.png");
		models.add(rect);
		
	}

}
