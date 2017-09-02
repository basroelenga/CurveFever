package bas.com.curve.shapes;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import bas.com.curve.math.Vector3f;
import bas.com.curve.utils.BufferUtils;

import static android.opengl.GLES30.*;

public class Quad {

	private Triangle triangle1;
	private Triangle triangle2;
	
	private float[] vertexData;
	
	private int vaoID;
	
	public Quad()
	{
		
		ArrayList<Vector3f> points = new ArrayList<Vector3f>();
		
		points.add(new Vector3f(0.0f, 0.0f, 0.0f));
		points.add(new Vector3f(1.0f, 0.0f, 0.0f));
		points.add(new Vector3f(1.0f, 1.0f, 0.0f));
		points.add(new Vector3f(0.0f, 1.0f, 0.0f));
		
		// Store the data in triangles
		triangle1 = new Triangle(points.get(0), points.get(1), points.get(2));
		triangle2 = new Triangle(points.get(0), points.get(3), points.get(2));
		
		FloatBuffer vertexData = BufferUtils.createFloatBuffer(getVertexData().length);
		vertexData.put(this.getVertexData());
		vertexData.flip();
		
		// Generate and store texture coordinates
		float[] texCoords = {
				
				0f, 1f,
				1f, 1f,
				1f, 0f,
				
				0f, 1f,
				0f, 0f,
				1f, 0f
		};
		
		FloatBuffer textureData = BufferUtils.createFloatBuffer(texCoords.length);
		textureData.put(texCoords);
		textureData.flip();
		
		int[] vaoIDArray = new int[1];
		glGenVertexArrays(1, vaoIDArray, 0);
		
		vaoID = vaoIDArray[0];
		glBindVertexArray(vaoID);
		
		int[] vboVIDArray = new int[1];
		glGenBuffers(1, vboVIDArray, 0);
		
		int vboVID = vboVIDArray[0];
		glBindBuffer(GL_ARRAY_BUFFER, vboVID);
		
		// Buffer the vertex data
		glBufferData(GL_ARRAY_BUFFER, vertexData.capacity() * 4, vertexData, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		// Buffer the texture data
		int[] vboTIDArray = new int[1];
		glGenBuffers(1, vboTIDArray, 0);
		
		int vboTID = vboTIDArray[0];
		glBindBuffer(GL_ARRAY_BUFFER, vboTID);
		
		glBufferData(GL_ARRAY_BUFFER, vertexData.capacity() * 4, vertexData, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	private float[] getVertexData()
	{
		
		vertexData = new float[18];
		
		for(int i = 0; i < 9; i++)
		{
			
			vertexData[i] = triangle1.getTriangleVertexData()[i];
			vertexData[i + 9] = triangle2.getTriangleVertexData()[i];
		}
		
		return vertexData;
	}
	
	public int getVaoID()
	{
		return vaoID;
	}
}