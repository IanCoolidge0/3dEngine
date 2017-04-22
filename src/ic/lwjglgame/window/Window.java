package ic.lwjglgame.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

	private int width;
	private int height;
	private String title;
	
	private long windowId;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	public long init() {
		GLFW.glfwInit();
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		windowId = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		
		GLFW.glfwSetWindowPos(windowId, 100, 100);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwMakeContextCurrent(windowId);
		GLFW.glfwShowWindow(windowId);
		
		GL.createCapabilities();
		
		return windowId;
	}
	
	public void run() {
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(windowId);
	}

	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(windowId);
	}

	public void destroy() {
		GLFW.glfwTerminate();
		GL.destroy();
	}
	
	

}
