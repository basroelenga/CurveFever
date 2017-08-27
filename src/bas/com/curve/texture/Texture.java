package bas.com.curve.texture;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import static android.opengl.GLES30.*;

public class Texture {

	private String name;
	private Context context;
	
	private Bitmap bm = null;
	private int textureID;
	
	public Texture(String name, Context context)
	{
		
		this.name = name;
		this.context = context;
		
		loadTexture();
		setOpenGLTexture();
	}

	private void loadTexture()
	{
		
		try 
		{
			
			// Load the file
			String fullName = "textures/" + name + ".png"; 
			InputStream is = context.getAssets().open(fullName);
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;
			
			// Get the file to a bitmap
			bm = BitmapFactory.decodeStream(is, null, options);
			
			// Close the stream
			is.close();
		} catch (IOException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	private void setOpenGLTexture()
	{
		
		int[] textureIDArray = new int[1];
		glGenTextures(1, textureIDArray, 0);
		
		textureID = textureIDArray[0];
		
		// Perform check if there is information in the bitmap
		if(bm == null)
		{
			
			System.err.println("Could not decode bitmap of " + name);
			
			glDeleteTextures(1, textureIDArray, 0);
			return;

		}
		
		glBindTexture(GL_TEXTURE_2D, textureID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		GLUtils.texImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bm, 0);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		bm.recycle();
	}
	
	public void destroy()
	{
		
		int[] textureIDArray = new int[1];
		textureIDArray[0] = textureID;
		
		glDeleteTextures(1, textureIDArray, 0);
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getTextureID()
	{
		return textureID;
	}
}