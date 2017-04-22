package ic.lwjglgame.shaders;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL20;

import ic.lwjglgame.math.Matrix4f;

public class ShaderProgram {

	private int id;
	
	private Shader vertexShader;
	private Shader fragmentShader;
	
	private int loc_modelMatrix;
	private int loc_projectionMatrix;
	private int loc_viewMatrix;
	
	public ShaderProgram(String vertexPath, String fragmentPath) {
		vertexShader = new Shader(vertexPath, ARBVertexShader.GL_VERTEX_SHADER_ARB);
		fragmentShader = new Shader(fragmentPath, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		
		id = ARBShaderObjects.glCreateProgramObjectARB();
		
		ARBShaderObjects.glAttachObjectARB(id, vertexShader.getId());
		ARBShaderObjects.glAttachObjectARB(id, fragmentShader.getId());
		
		ARBShaderObjects.glLinkProgramARB(id);
		ARBShaderObjects.glValidateProgramARB(id);
		
		loadUniforms();
	}
	
	public void loadUniforms() {
		loc_modelMatrix = GL20.glGetUniformLocation(id, "modelMatrix");
		loc_projectionMatrix = GL20.glGetUniformLocation(id, "projectionMatrix");
		loc_viewMatrix = GL20.glGetUniformLocation(id, "viewMatrix");
	}
	
	public void loadUniformModelMatrix(Matrix4f modelMatrix) {
		GL20.glUniformMatrix4fv(loc_modelMatrix, false, modelMatrix.buffer());
	}
	
	public void loadUniformProjectionMatrix(Matrix4f projectionMatrix) {
		GL20.glUniformMatrix4fv(loc_projectionMatrix, false, projectionMatrix.buffer());
	}
	
	public void loadUniformViewMatrix(Matrix4f viewMatrix) {
		GL20.glUniformMatrix4fv(loc_viewMatrix, false, viewMatrix.buffer());
	}
	
	public void loadShaders() {
		ARBShaderObjects.glUseProgramObjectARB(id);
	}
	
	public void unloadShaders() {
		ARBShaderObjects.glUseProgramObjectARB(0);
	}

}
