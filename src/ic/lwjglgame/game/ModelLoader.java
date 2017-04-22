package ic.lwjglgame.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import ic.lwjglgame.util.Util;

public class ModelLoader {

	private static ArrayList<Integer> vaos;
	private static ArrayList<Integer> vbos;
	
	public static void init() {
		vaos = new ArrayList<Integer>();
		vbos = new ArrayList<Integer>();
	}
	
	public static Model loadVAOFromOBJ(String path) {
		ArrayList<Float> vertices = new ArrayList<Float>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		String file = Util.readFileAsString(path);
		
		BufferedReader reader = new BufferedReader(new StringReader(file));
		String line = null;
		try {
			while((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				
				switch(tokens[0]) 
				{
				case "v":
					vertices.add(Float.parseFloat(tokens[1]));
					vertices.add(Float.parseFloat(tokens[2]));
					vertices.add(Float.parseFloat(tokens[3]));
					break;
					
				case "f":
					for(int i=1;i<4;i++) {
						String[] token = tokens[i].split("/");
						indices.add(Integer.parseInt(token[0])-1);
					}
					break;
				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		float[] arVertices = new float[vertices.size()];
		int[] arIndices = new int[indices.size()];
		
		for(int i=0; i<arVertices.length; i++)
			arVertices[i] = vertices.get(i);
		for(int i=0; i<arIndices.length; i++)
			arIndices[i] = indices.get(i);

		return loadVAOFromArray(arVertices, arIndices);
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
