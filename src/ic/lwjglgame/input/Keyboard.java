package ic.lwjglgame.input;

import org.lwjgl.glfw.GLFW;

public class Keyboard {

	private long window;
	
	public Keyboard(long window) {
		this.window = window;
	}
	
	public boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(window, key) != 0;
	}
	
}
