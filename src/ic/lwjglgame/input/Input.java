package ic.lwjglgame.input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Input {

	private long window;
	
	private float mouseX;
	private float mouseY;
	
	private float lastMouseX;
	private float lastMouseY;
	
	public Input(long window) {
		this.window = window;
	}
	
	public void update() {
		
		DoubleBuffer xbuf = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer ybuf = BufferUtils.createDoubleBuffer(1);
		
		GLFW.glfwGetCursorPos(window, xbuf, ybuf);
		
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		
		mouseX = (float) xbuf.get(0);
		mouseY = (float) ybuf.get(0);
	}
	
	public boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(window, key) != 0;
	}
	
	public boolean isButtonDown(int button) {
		return GLFW.glfwGetMouseButton(window, button) != 0;
	}

	public float getMouseX() {
		return mouseX;
	}

	public float getMouseY() {
		return mouseY;
	}

	public float getLastMouseX() {
		return lastMouseX;
	}

	public float getLastMouseY() {
		return lastMouseY;
	}
	
}
