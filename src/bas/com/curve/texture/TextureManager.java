package bas.com.curve.texture;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

public class TextureManager {

	private static ArrayList<Texture> textureList = new ArrayList<Texture>();
	
	private TextureManager() {}
	
	public static void loadTextures(Context context)
	{
		
		try 
		{
			
			// Get all the textures from the assets/textures folder
			String[] textures = context.getAssets().list("textures");
			
			for(String texture : textures)
			{
				
				// Get the name of the texture
				String name = texture.split(".")[0];
				
				// Construct the texture
				textureList.add(new Texture(name, context));
			}
			
		} catch (IOException e) 
		{

			e.printStackTrace();
		}
	}
	
	public Texture getTexture(String name)
	{
		
		for(Texture texture : textureList)
		{
			if(texture.getName().equals(name)) return texture;
		}
		
		throw new RuntimeException("Texture does not exist: " + name);
	}
	
	public static void destroy()
	{
		
		for(Texture texture : textureList)
		{
			texture.destroy();
		}
	}
}