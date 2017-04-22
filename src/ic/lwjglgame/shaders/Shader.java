package ic.lwjglgame.shaders;

import org.lwjgl.opengl.ARBShaderObjects;

import ic.lwjglgame.util.Util;

public class Shader {

	private int id;
	
	public Shader(String path, int type) {
		try {
			id = ARBShaderObjects.glCreateShaderObjectARB(type);
			
			ARBShaderObjects.glShaderSourceARB(id, Util.readFileAsString(path));
			ARBShaderObjects.glCompileShaderARB(id);
			
		} catch(Exception e) {
			ARBShaderObjects.glDeleteObjectARB(id);
		}
	}
	
	public int getId() {
		return id;
	}

}
