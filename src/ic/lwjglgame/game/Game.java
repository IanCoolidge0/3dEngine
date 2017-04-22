package ic.lwjglgame.game;

import ic.lwjglgame.input.Input;

public class Game extends BaseGame {

	 Model rect;
	
	 float[] vertices = {
             -0.5f, 0.5f, 0f,
             -0.5f, -0.5f, 0f,
             0.5f, -0.5f, 0f,
             0.5f, 0.5f, 0f
     };
	 
	 float[] textureCoords = {
		0,0,
		0,1,
		1,1,
		1,0
	 };

	 float[] normals = {
		0,0,0,
		0,0,0,
		0,0,0,
		0,0,0
	 };

     int[] indices = {
             0, 1, 2,
             2, 3, 0
     };
     
     @Override
     public void update() {
    	 super.update();
     }
	
	public Game(Input keyboard) {
		super(keyboard);
		
		rect = ModelLoader.loadVAOFromOBJ("src/models/stall.obj", "src/models/stallTexture.png");
		rect.setScale(0.1f);
		models.add(rect);
		
	}

}
