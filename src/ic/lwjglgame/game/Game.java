package ic.lwjglgame.game;

import ic.lwjglgame.input.Input;
import ic.lwjglgame.math.Vector3f;

public class Game extends BaseGame {

	 Model rect;
	
	 float[] vertices = {
             -0.5f, 0.5f, 0f,
             -0.5f, -0.5f, 0f,
             0.5f, -0.5f, 0f,
             0.5f, -0.5f, 0f,
             0.5f, 0.5f, 0f,
             -0.5f, 0.5f, 0f
     };
	 
	 float[] textureCoords = {
		0,0,
		0,1,
		1,1,
		1,1,
		1,0,
		0,0
	 };

	 float[] normals = {
		0,0,0,
		0,0,0,
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
		
		rect = ModelLoader.loadVAOFromOBJ("src/models/house.obj", "src/models/house.jpg");
		rect.setScale(0.05f);
		models.add(rect);
		
	}

}
