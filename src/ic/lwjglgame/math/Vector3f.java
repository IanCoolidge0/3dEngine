package ic.lwjglgame.math;

public class Vector3f {

	private float x;
	private float y;
	private float z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public Vector3f add(Vector3f v) {
		return new Vector3f(x + v.getX(), y + v.getY(), z + v.getZ());
	}
	
	public Vector3f negate() {
		return new Vector3f(-x, -y, -z);
	}
	
	public float dot(Vector3f v) {
		return x * v.getX() + y * v.getY() + z * v.getZ();
	}
	
	public Vector3f cross(Vector3f v) {
		return new Vector3f(y * v.getZ() - z * v.getY(),
						    z * v.getX() - x * v.getZ(),
						    x * v.getY() - y * v.getX());
	}

}
