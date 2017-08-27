package bas.com.curve.shaders;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

public class ShaderManager {
	
	private static ArrayList<Shader> shaderList = new ArrayList<Shader>();
	private static ArrayList<String> nameList = new ArrayList<String>();
	
	private ShaderManager() {}
	
	public static void constructShaders(Context context)
	{
		
		try 
		{
			
			// Get the names of all the shaders
			String[] shaders = context.getAssets().list("shaders");
			
			for(String shader : shaders)
			{
				
				// Get the name from the file
				String name = shader.split("_")[0];
				
				// Check if the name is already in the list
				boolean inList = false;
				
				for(String name_in_list : nameList)
				{
					if(name_in_list.equals(name)) inList = true;
				}
				
				if(!inList) nameList.add(name);
			}
			
			// Construct all shaders and add them to the list
			for(String name : nameList) shaderList.add(new Shader(context, name));
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	public static Shader getShader(String name)
	{
		
		for(Shader shader : shaderList)
		{
			if(shader.getName().equals(name)) return shader;
		}
		
		throw new RuntimeException("Shader does not exist: " + name);
	}
	
	public static void destroy()
	{
		
		for(Shader shader : shaderList)
		{
			shader.destroy();
		}
	}
}