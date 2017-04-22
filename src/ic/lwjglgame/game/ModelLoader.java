package ic.lwjglgame.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import ic.lwjglgame.util.Util;

public class ModelLoader {

	private static ArrayList<Integer> vaos;
	private static ArrayList<Integer> vbos;
	private static ArrayList<Integer> textures;
	
	public static void init() {
		vaos = new ArrayList<Integer>();
		vbos = new ArrayList<Integer>();
		textures = new ArrayList<Integer>();
	}
	
	public static int loadTexture(String path) {
		int id = GL11.glGenTextures();
		
		ByteBuffer image;
		int width, height;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			
			image = STBImage.stbi_load(path, w, h, comp, 4);
			
			width = w.get();
			height = h.get();
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		
		return id;
	}
	
	public static Model loadVAOFromOBJ(String path, String texturePath) {
		ArrayList<Float> texCoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		ArrayList<Float> vertices = new ArrayList<Float>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		float[] arTexCoords = null;
		float[] arNormals = null;
		
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
				
				case "vt":
					texCoords.add(Float.parseFloat(tokens[1]));
					texCoords.add(Float.parseFloat(tokens[2]));
					break;
					
				case "vn":
					normals.add(Float.parseFloat(tokens[1]));
					normals.add(Float.parseFloat(tokens[2]));
					normals.add(Float.parseFloat(tokens[3]));
					break;
					
				case "f":
					if(arTexCoords == null) {
						arTexCoords = new float[(int)(vertices.size()*2.0/3)];
						arNormals = new float[vertices.size()];
					}
					for(int i=1;i<4;i++) {
						String[] token = tokens[i].split("/");
						
						int vertPtr = Integer.parseInt(token[0]) - 1;
						
						indices.add(vertPtr);
						
						arTexCoords[vertPtr*2] = texCoords.get(Integer.parseInt(token[1])-1);
						arTexCoords[vertPtr*2 + 1] = texCoords.get(Integer.parseInt(token[1]));
						
						arNormals[vertPtr*2] = normals.get(Integer.parseInt(token[2])-1);
						arNormals[vertPtr*2 + 1] = normals.get(Integer.parseInt(token[2]));
						arNormals[vertPtr*2 + 2] = normals.get(Integer.parseInt(token[2])+1);
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
		
		return loadVAOFromArray(arVertices, arTexCoords, arNormals, arIndices, texturePath);
	}
	
	public static Model loadVAOFromArray(float[] vertices, float[] textureCoords, float[] normals, int[] indices, String texturePath) {
		int vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		vaos.add(vaoId);
		
		loadFloatBuffer(0, 3, vertices);
		loadFloatBuffer(1, 2, textureCoords);
		loadFloatBuffer(2, 3, normals);
		
		GL30.glBindVertexArray(0);
		
		int indicesId = GL15.glGenBuffers();
		vbos.add(indicesId);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, genIntBuffer(indices), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		int textureID = loadTexture(texturePath);
		
		return new Model(vaoId, indicesId, indices.length, textureID);
	}
	
		
	private static void loadFloatBuffer(int attrib, int size, float[] data) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, genFloatBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, size, GL11.GL_FLOAT, false, 0, 0);
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
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		for(int vao: vaos)
			GL30.glDeleteVertexArrays(vao);
		for(int vbo: vbos)
			GL15.glDeleteBuffers(vbo);
		for(int texture: textures)
			GL11.glDeleteTextures(texture);
		
	}

}
