package ic.lwjglgame.game;

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

     }
	
	public Game() {
		super();
		
		rect = ModelLoader.loadVAOFromArray(vertices, indices);
		models.add(rect);
		
	}

}
