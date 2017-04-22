package ic.lwjglgame.game;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelLoader {

	private static ArrayList<Integer> vaos;
	private static ArrayList<Integer> vbos;
	
	public static void init() {
		vaos = new ArrayList<Integer>();
		vbos = new ArrayList<Integer>();
	}
	
	public static Model loadVAOFromArray(float[] vertices, int[] indices) {
		int vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		vaos.add(vaoId);
		
		loadFloatBuffer(0, vertices);
		
		GL30.glBindVertexArray(0);
		
		int indicesId = GL15.glGenBuffers();
		vbos.add(indicesId);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, genIntBuffer(indices), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		return new Model(vaoId, indicesId, indices.length);
	}
	
		
	private static void loadFloatBuffer(int attrib, float[] data) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, genFloatBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static FloatBuffer genFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static IntBuffer genIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public static void destroy() {
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		for(int vao: vaos)
			GL30.glDeleteVertexArrays(vao);
		for(int vbo: vbos)
			GL15.glDeleteBuffers(vbo);
		
	}

}
