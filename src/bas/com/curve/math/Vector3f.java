package bas.com.curve.math;

public class Vector3f {

	private float x;
	private float y;
	private float z;
	
	public Vector3f()
	{
		
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3f(float x, float y, float z)
	{
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void add(Vector3f vec)
	{
		
		x += vec.getX();
		y += vec.getY();
		z += vec.getZ();
	}
	
	public void add(float f)
	{
		
		x += f;
		y += f;
		z += f;
	}
	
	public void scale(float f)
	{
		
		x *= f;
		y *= f;
		z *= f;
	}

	public void negate()
	{
		
		x = -x;
		y = -y;
		z = -z;
	}
	
	public void dot(Vector3f vec)
	{
		
		x *= vec.getX();
		y *= vec.getY();
		z *= vec.getZ();
	}
	
	public void cross(Vector3f vec)
	{
		
		float tempX = x;
		float tempY = y;
		float tempZ = z;
		
		x = tempY * vec.getZ() - tempZ * vec.getY();
		y = tempZ * vec.getX() - tempX * vec.getZ();
		z = tempX * vec.getY() - tempY * vec.getX();
	}
	
	public void normalize()
	{
		
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		
		x = x / length;
		y = y / length;
		z = z / length;
	}
	
	public void print()
	{
		System.out.println(x + " , " + y + " , " + z);
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
}