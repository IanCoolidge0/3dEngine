package ic.lwjglgame.game;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import ic.lwjglgame.input.Keyboard;
import ic.lwjglgame.math.Matrix4f;
import ic.lwjglgame.shaders.ShaderProgram;

public class BaseGame {	
	
	public static final float FOV = 70f;
	public static final float FAR_PLANE = 1000f;
	public static final float NEAR_PLANE = 0.1f;

	protected ArrayList<Model> models;
	protected Keyboard keyboard;
	
	private ShaderProgram shaderProgram;
	private Matrix4f projectionMatrix;
	private Camera camera;
	
	public BaseGame(Keyboard keyboard) {
		ModelLoader.init();
		this.models = new ArrayList<Model>();
		this.camera = new Camera();
		this.shaderProgram = new ShaderProgram("src/ic/lwjglgame/shaders/basic.vs", 
											   "src/ic/lwjglgame/shaders/basic.fs");
		this.projectionMatrix = new Matrix4f();
		this.keyboard = keyboard;
		projectionMatrix.loadProjection(FOV, FAR_PLANE, NEAR_PLANE);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void update() {
		camera.update(keyboard);
	}
	
	public void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		shaderProgram.loadShaders();
		
		for(Model model: models) {
			GL30.glBindVertexArray(model.getVaoId());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);

			shaderProgram.loadUniformModelMatrix(model.getModelMatrix());
			shaderProgram.loadUniformProjectionMatrix(projectionMatrix);
			shaderProgram.loadUniformViewMatrix(camera.getViewMatrix());
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture());
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, model.getIndicesId());
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getSize(), GL11.GL_UNSIGNED_INT, 0);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
		}
		
		shaderProgram.unloadShaders();
	}
	
	public void destroy() {
		ModelLoader.destroy();
	}

}
