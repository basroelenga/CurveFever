package bas.com.curve.shaders;

import static android.opengl.GLES20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import bas.com.curve.math.Matrix4f;
import bas.com.curve.math.Vector3f;

public class Shader {

	private String name;
	private Context context;
	
	private int vertexID;
	private int fragmentID;
	
	private int shaderProgram;
	
	private int modelMatrixLoc;
	private int viewMatrixLoc;
	private int projectionMatrixLoc;
	
	public Shader(Context context, String name)
	{
		
		this.name = name;
		this.context = context;
		
		// Load the data of the shader
		String vertexShaderSource = loadShader("vertex");
		String fragmentShaderSource = loadShader("fragment");
		
		// Compile both parts of the shader
		vertexID = compileShader(vertexShaderSource, GL_VERTEX_SHADER);
		fragmentID = compileShader(fragmentShaderSource, GL_FRAGMENT_SHADER);
		
		// Link the shader
		linkShaderProgram(vertexID, fragmentID);
		
		// Bind the attribute locations
		glBindAttribLocation(shaderProgram, 0, "position");
		glBindAttribLocation(shaderProgram, 1, "texture_position");
		
		// Set the location of the matrices
		modelMatrixLoc = glGetUniformLocation(shaderProgram, "modelMatrix");	
		viewMatrixLoc = glGetUniformLocation(shaderProgram, "viewMatrix");
		projectionMatrixLoc = glGetUniformLocation(shaderProgram, "projectionMatrix");
		
		
		
	}
	
	private String loadShader(String type)
	{
		
		// Reconstruct the full name of the shader
		String fullName = name + "_" + type + "_shader.glsl";
		
		// Create data holder
		StringBuilder shaderBuilder = new StringBuilder();
		
		try {
			
			// Load the data from the assets folder
			BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("shaders/" + fullName)));
			String line;
			
			while((line = reader.readLine()) != null)
			{
				
				shaderBuilder.append(line);
				shaderBuilder.append("\n");
			}
			
			reader.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return shaderBuilder.toString();
	}
	
	private int compileShader(String shaderSource, int shaderType){
		
		int shaderID = glCreateShader(shaderType);
		
		if(shaderID == 0){
			System.err.println("Shader could not be created");
			return 0;
		}
		
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		
		int[] compileStatus = new int[1];
		glGetShaderiv(shaderID, GL_COMPILE_STATUS, compileStatus, 0);
		
		if(compileStatus[0] == 0){
			System.err.println("Shader could not be compiled: \n" + name + "\n" + shaderSource);
			System.err.println(glGetShaderInfoLog(shaderID));
			return 0;
		}
		
		return shaderID;
	}
	
	private void linkShaderProgram(int vertexID, int fragmentID){
		
		shaderProgram = glCreateProgram();
		
		if(shaderProgram == 0){
			
			System.err.println("Shader program could not be created");
			return;
		}
		
		glAttachShader(shaderProgram, vertexID);
		glAttachShader(shaderProgram, fragmentID);
		
		glLinkProgram(shaderProgram);
		
		int[] linkStatus = new int[1];
		glGetProgramiv(shaderProgram, GL_LINK_STATUS, linkStatus, 0);
		
		if(linkStatus[0] == 0){
			
			System.err.println("Shaders could not be linked");
			System.err.println(glGetProgramInfoLog(shaderProgram));
			
			glDeleteProgram(shaderProgram);
			return;
		}
		
		glValidateProgram(shaderProgram);
		
		int[] validateStatus = new int[1];
		glGetProgramiv(shaderProgram, GL_VALIDATE_STATUS, validateStatus, 0);
		
		if(validateStatus[0] == 0){
			
			System.err.println("Shader program could not be validated");
			System.err.println(glGetProgramInfoLog(shaderProgram));
		}
	}
	
	public void uploadInt(int integer, int location)
	{
		
		bind();
		
		glUniform1i(location, integer);
		
		unbind();
	}
	
	public void uploadFloat(float f, int location)
	{
		
		bind();
		
		glUniform1f(location, f);
		
		unbind();
	}
	
	public void uploadVector3f(Vector3f vector, int location)
	{
		
		bind();
		
		glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
		
		unbind();
	}
	
	public void uploadMatrix4f(Matrix4f matrix, int location)
	{
		
		bind();
		
		glUniformMatrix4fv(location, 1, true, matrix.toFloatBuffer());
		
		unbind();
	}
	
	public void bind()
	{
		
		glUseProgram(shaderProgram);
	}
	
	public void unbind()
	{
		
		glUseProgram(0);
	}
	
	public void destroy()
	{
		
		glDetachShader(shaderProgram, vertexID);
		glDetachShader(shaderProgram, fragmentID);
		
		glDeleteShader(vertexID);
		glDeleteShader(fragmentID);
		
		glDeleteProgram(shaderProgram);
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getShaderProgram()
	{
		return shaderProgram;
	}
	
	public int getModelMatrixLoc()
	{
		return modelMatrixLoc;
	}
	
	public int getViewMatrixLoc()
	{
		return viewMatrixLoc;
	}
	
	public int getProjectionMatrixLoc()
	{
		return projectionMatrixLoc;
	}
}