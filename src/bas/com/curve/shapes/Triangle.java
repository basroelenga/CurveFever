package bas.com.curve.shapes;

import bas.com.curve.math.Vector3f;

public class Triangle {

	private Vector3f first;
	private Vector3f second;
	private Vector3f third;
	
	private float[] triangleData;
	
	public Triangle(Vector3f first, Vector3f second, Vector3f third)
	{
		
		this.first = first;
		this.second = second;
		this.third = third;
		
		setTriangleData();
	}
	
	private void setTriangleData()
	{
		
		triangleData = new float[9];
		
		triangleData[0] = first.getX();
		triangleData[1] = first.getY();
		triangleData[2] = first.getZ();
		
		triangleData[3] = second.getX();
		triangleData[4] = second.getY();
		triangleData[5] = second.getZ();
		
		triangleData[6] = third.getX();
		triangleData[7] = third.getY();
		triangleData[8] = third.getZ();
	}
	
	public float[] getTriangleVertexData()
	{
		return triangleData;
	}
}