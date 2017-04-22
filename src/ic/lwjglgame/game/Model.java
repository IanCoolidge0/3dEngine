package ic.lwjglgame.game;

import ic.lwjglgame.math.Matrix4f;
import ic.lwjglgame.math.Vector3f;

public class Model {

	private int vaoId;
	private int indicesId;
	private int size;
	
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	
	public Model(int vaoId, int indicesId, int size, Vector3f position, Vector3f rotation, float scale) {
		this.vaoId = vaoId;
		this.indicesId = indicesId;
		this.size = size;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Model(int vaoId, int indicesId, int size) {
		this.vaoId = vaoId;
		this.indicesId = indicesId;
		this.size = size;
		this.position = new Vector3f(0,0,0);
		this.rotation = new Vector3f(0,0,0);
		this.scale = 1;
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
	
	public Matrix4f getModelMatrix() {
		return new Matrix4f().scale(scale).rotate(rotation).translate(position);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void translate(Vector3f d) {
		position = position.add(d);
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public void rotate(Vector3f r) {
		rotation = rotation.add(r);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
