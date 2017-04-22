package ic.lwjglgame.game;

import org.lwjgl.glfw.GLFW;

import ic.lwjglgame.input.Keyboard;
import ic.lwjglgame.math.Matrix4f;
import ic.lwjglgame.math.Vector3f;

public class Camera {
	
	public static final float TURN_SPEED = 0.05f;
	public static final float RUN_SPEED = 0.001f;

	private Vector3f pos;
	private Vector3f angle;
	
	public Camera(Vector3f pos, Vector3f angle) {
		this.pos = pos;
		this.angle = angle;
	}
	
	public Camera() {
		this.pos = new Vector3f(0.2f,0,2f);
		this.angle = new Vector3f(0,0,0);
	}
	
	public void update(Keyboard keyboard) {
		if(keyboard.isKeyDown(GLFW.GLFW_KEY_D)) 
			this.rotate(new Vector3f(0,-TURN_SPEED,0));
		if(keyboard.isKeyDown(GLFW.GLFW_KEY_A))
			this.rotate(new Vector3f(0,+TURN_SPEED,0));
		
		float yaw = (float) Math.toRadians(angle.getY());

		if(keyboard.isKeyDown(GLFW.GLFW_KEY_W)) 
			this.translate(new Vector3f(-RUN_SPEED * (float) Math.sin(yaw), 0, -RUN_SPEED * (float) Math.cos(yaw)));
		if(keyboard.isKeyDown(GLFW.GLFW_KEY_S)) 
			this.translate(new Vector3f(RUN_SPEED * (float) Math.sin(yaw), 0, RUN_SPEED * (float) Math.cos(yaw)));
	}
	
	public Matrix4f getViewMatrix() {
		return new Matrix4f().translate(pos.negate()).rotate(angle.negate());
	}

	public Vector3f getPosition() {
		return pos;
	}

	public void setPosition(Vector3f pos) {
		this.pos = pos;
	}
	
	public void translate(Vector3f d) {
		pos = pos.add(d);
	}

	public Vector3f getAngle() {
		return angle;
	}

	public void setAngle(Vector3f angle) {
		this.angle = angle;
	}
	
	public void rotate(Vector3f r) {
		angle = angle.add(r);
	}

}
