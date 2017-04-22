package ic.lwjglgame.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import ic.lwjglgame.window.Main;

public class Matrix4f {
	
	public float  m00, m01, m02, m03,
	  			  m10, m11, m12, m13,
	  			  m20, m21, m22, m23,
	  			  m30, m31, m32, m33;
	
	public Matrix4f() {
		loadIdentity();
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(m00).append(' ').append(m10).append(' ').append(m20).append(' ').append(m30).append('\n');
		buf.append(m01).append(' ').append(m11).append(' ').append(m21).append(' ').append(m31).append('\n');
		buf.append(m02).append(' ').append(m12).append(' ').append(m22).append(' ').append(m32).append('\n');
		buf.append(m03).append(' ').append(m13).append(' ').append(m23).append(' ').append(m33).append('\n');
		return buf.toString();
	}
	
	public void load(Matrix4f mat) {
		m00 = mat.m00; m01 = mat.m01; m02 = mat.m02; m03 = mat.m03;
		m10 = mat.m10; m11 = mat.m11; m12 = mat.m12; m13 = mat.m13;
		m20 = mat.m20; m21 = mat.m21; m22 = mat.m22; m23 = mat.m23;
		m30 = mat.m30; m31 = mat.m31; m32 = mat.m32; m33 = mat.m33;
	}
	
	public void loadScalar(float s) {
		m00 = s; m01 = 0; m02 = 0; m03 = 0;
		m10 = 0; m11 = s; m12 = 0; m13 = 0;
		m20 = 0; m21 = 0; m22 = s; m23 = 0;
		m30 = 0; m31 = 0; m32 = 0; m33 = s;
	}
	
	public void loadIdentity() {
		m00 = 1; m01 = 0; m02 = 0; m03 = 0;
		m10 = 0; m11 = 1; m12 = 0; m13 = 0;
		m20 = 0; m21 = 0; m22 = 1; m23 = 0;
		m30 = 0; m31 = 0; m32 = 0; m33 = 1;
	}
	
	public void loadTranslation(Vector3f translation) {
		loadIdentity();
		m03 = translation.getX();
		m13 = translation.getY();
		m23 = translation.getZ();
	}
	
	public void loadRotation(Vector3f rotation) {
		float rx = (float) Math.toRadians(rotation.getX());
		float ry = (float) Math.toRadians(rotation.getY());
		float rz = (float) Math.toRadians(rotation.getZ());
		
		Matrix4f matX = new Matrix4f();
		matX.m11 = (float)  Math.cos(rx);
		matX.m22 = (float)  Math.cos(rx);
		matX.m12 = (float) -Math.sin(rx);
		matX.m21 = (float)  Math.sin(rx);
		
		Matrix4f matY = new Matrix4f();
		matY.m00 = (float)  Math.cos(rz);
		matY.m22 = (float)  Math.cos(rz);
		matY.m02 = (float)  Math.sin(rz);
		matY.m20 = (float) -Math.sin(rz);
		
		Matrix4f matZ = new Matrix4f();
		matZ.m00 = (float)  Math.cos(ry);
		matZ.m11 = (float)  Math.cos(ry);
		matZ.m01 = (float) -Math.sin(ry);
		matZ.m10 = (float)  Math.sin(ry);
		
		load(matX.multiply(matY).multiply(matZ));
	}
	
	public void loadProjection(float fov, float farPlane, float nearPlane) {
		float aspect = Main.WIDTH / Main.HEIGHT;
		float scly = 1.0f * aspect / (float) Math.tan(Math.toRadians(fov / 2.0f));
		float sclx = scly / aspect;
		float frustum = farPlane - nearPlane;
		
		loadIdentity();
		
		m00 = sclx;
		m11 = scly;
		m22 = - ((farPlane + nearPlane) / frustum);
		m32 = -1;
		m23 = - ((2 * farPlane * nearPlane) / frustum);
		m33 = 0;
	}
	
	public Matrix4f multiply(Matrix4f other) {
        Matrix4f result = new Matrix4f();

        result.m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30;
        result.m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30;
        result.m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30;
        result.m30 = this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30;

        result.m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31;
        result.m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31;
        result.m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31;
        result.m31 = this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31;

        result.m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32;
        result.m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32;
        result.m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32;
        result.m32 = this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32;

        result.m03 = this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33;
        result.m13 = this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33;
        result.m23 = this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33;
        result.m33 = this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33;

        return result;
    }

	public FloatBuffer buffer() {
		FloatBuffer buf = BufferUtils.createFloatBuffer(16);
		
		buf.put(m00).put(m10).put(m20).put(m30);
		buf.put(m01).put(m11).put(m21).put(m31);
		buf.put(m02).put(m12).put(m22).put(m32);
		buf.put(m03).put(m13).put(m23).put(m33);
		buf.flip();
		
		return buf;
	}
	
	public Matrix4f rotate(Vector3f rotation) {
		float rx = (float) Math.toRadians(rotation.getX());
		float ry = (float) Math.toRadians(rotation.getY());
		float rz = (float) Math.toRadians(rotation.getZ());
		
		Matrix4f matX = new Matrix4f();
		matX.m11 = (float)  Math.cos(rx);
		matX.m22 = (float)  Math.cos(rx);
		matX.m12 = (float) -Math.sin(rx);
		matX.m21 = (float)  Math.sin(rx);
		
		Matrix4f matY = new Matrix4f();
		matY.m00 = (float)  Math.cos(ry);
		matY.m22 = (float)  Math.cos(ry);
		matY.m02 = (float)  Math.sin(ry);
		matY.m20 = (float) -Math.sin(ry);
		
		Matrix4f matZ = new Matrix4f();
		matZ.m00 = (float)  Math.cos(rz);
		matZ.m11 = (float)  Math.cos(rz);
		matZ.m01 = (float) -Math.sin(rz);
		matZ.m10 = (float)  Math.sin(rz);
		
		return matX.multiply(matY).multiply(matZ).multiply(this);
	}
	
	public Matrix4f translate(Vector3f translation) {
		Matrix4f trans = new Matrix4f();
		trans.m03 = translation.getX();
		trans.m13 = translation.getY();
		trans.m23 = translation.getZ();
		return trans.multiply(this);
	}

	public Matrix4f scale(float scale) {
		Matrix4f scl = new Matrix4f();
		scl.m00 = scale;
		scl.m11 = scale;
		scl.m22 = scale;
		return this.multiply(scl);
	}

}
