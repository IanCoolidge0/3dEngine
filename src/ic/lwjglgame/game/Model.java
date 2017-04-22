package ic.lwjglgame.game;

import ic.lwjglgame.math.Matrix4f;
import ic.lwjglgame.math.Vector3f;

public class Model {

	private int vaoId;
	private int indicesId;
	private int size;
	
	private Matrix4f modelMatrix;
	
	public Model(int vaoId, int indicesId, int size, Vector3f translation, Vector3f rotation, float scale) {
		this.vaoId = vaoId;
		this.indicesId = indicesId;
		this.size = size;
		this.modelMatrix = new Matrix4f().scale(scale).rotate(rotation).translate(translation);
	}
	
	public Model(int vaoId, int indicesId, int size) {
		this.vaoId = vaoId;
		this.indicesId = indicesId;
		this.size = size;
		this.modelMatrix = new Matrix4f();
	}
	
	public int getSize() {
		return size;
	}
	
	public int getVaoId() {
		return vaoId;
	}
	
	public int getIndicesId() {
		return indicesId;
	}
	
	public void translate(Vector3f d) {
		modelMatrix = modelMatrix.translate(d);
	}
	
	public void rotate(Vector3f d) {
		modelMatrix = modelMatrix.rotate(d);
	}

	public void scale(float d) {
		modelMatrix = modelMatrix.scale(d);
	}
	
	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}

}
